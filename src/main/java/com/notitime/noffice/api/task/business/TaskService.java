package com.notitime.noffice.api.task.business;

import static com.notitime.noffice.global.web.BusinessErrorCode.NOT_FOUND_ANNOUNCEMENT;
import static com.notitime.noffice.global.web.BusinessErrorCode.NOT_FOUND_TASK;

import com.notitime.noffice.api.task.presentation.dto.request.TaskModifyRequest;
import com.notitime.noffice.api.task.presentation.dto.request.TaskStatusUpdateRequest;
import com.notitime.noffice.api.task.presentation.dto.request.TaskStatusUpdateRequests;
import com.notitime.noffice.api.task.presentation.dto.response.AssignedTaskResponse;
import com.notitime.noffice.api.task.presentation.dto.response.TaskModifyResponse;
import com.notitime.noffice.api.task.presentation.dto.response.TaskResponse;
import com.notitime.noffice.api.task.presentation.dto.response.TaskResponses;
import com.notitime.noffice.domain.announcement.persistence.AnnouncementRepository;
import com.notitime.noffice.domain.member.persistence.MemberRepository;
import com.notitime.noffice.domain.organization.model.Organization;
import com.notitime.noffice.domain.organization.persistence.OrganizationMemberRepository;
import com.notitime.noffice.domain.task.model.Task;
import com.notitime.noffice.domain.task.model.TaskStatus;
import com.notitime.noffice.domain.task.persistence.TaskRepository;
import com.notitime.noffice.domain.task.persistence.TaskStatusRepository;
import com.notitime.noffice.global.exception.NotFoundException;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {

	private final OrganizationMemberRepository organizationMemberRepository;
	private final TaskRepository taskRepository;
	private final AnnouncementRepository announcementRepository;
	private final MemberRepository memberRepository;
	private final TaskStatusRepository taskStatusRepository;

	public TaskModifyResponse modify(TaskModifyRequest taskModifyRequest) {
		Task task = findById(taskModifyRequest.id());
		return TaskModifyResponse.from(task.modify(taskModifyRequest.content()));
	}

	public Slice<AssignedTaskResponse> getAssignedTasks(Long memberId, Pageable pageable) {
		Slice<Organization> organizations = getSlicedOrganizations(memberId, pageable);
		List<AssignedTaskResponse> responses = organizations.stream()
				.map(or -> assembleUncheckedTasks(or, memberId))
				.toList();
		return new PageImpl<>(responses, pageable, organizations.getSize());
	}

	public TaskResponses getTasksById(Long announcementId) {
		validateAnnouncement(announcementId);
		return TaskResponses.from(taskRepository.findByAnnouncementId(announcementId));
	}

	public void delete(Long announcementId, Long taskId) {
		validateAnnouncement(announcementId);
		taskRepository.deleteById(taskId);
	}

	private Task findById(Long taskId) {
		return taskRepository.findById(taskId)
				.orElseThrow(() -> new NotFoundException(NOT_FOUND_TASK));
	}

	private void validateAnnouncement(Long announcementId) {
		if (!announcementRepository.existsById(announcementId)) {
			throw new NotFoundException(NOT_FOUND_ANNOUNCEMENT);
		}
	}

	private Slice<Organization> getSlicedOrganizations(Long memberId, Pageable pageable) {
		return organizationMemberRepository.findOrganizationsByMemberId(memberId, pageable);
	}

	private AssignedTaskResponse assembleUncheckedTasks(Organization organization, Long memberId) {
		List<TaskStatus> assignedTasks = taskStatusRepository.findByTaskIdInAndMemberIdIn(
						getAllTasks(organization).stream().map(Task::getId).toList(),
						List.of(memberId)
				).stream()
				.filter(ts -> !ts.getIsChecked())
				.sorted(Comparator.comparing(TaskStatus::getCreatedAt).reversed())
				.limit(5)
				.toList();
		List<TaskResponse> assembled = assignedTasks.stream()
				.map(ts -> TaskResponse.from(ts.getTask(), ts.getIsChecked()))
				.toList();
		return AssignedTaskResponse.from(organization, assembled);
	}

	private List<Task> getAllTasks(Organization organization) {
		return organization.getAnnouncements().stream()
				.flatMap(announcement -> announcement.getTasks().stream())
				.toList();
	}

	public void updateTaskStatus(Long memberId, TaskStatusUpdateRequests request) {
		List<Long> taskIds = request.tasks().stream().map(TaskStatusUpdateRequest::id).toList();
		List<TaskStatus> taskStatuses = taskStatusRepository.findByTaskIdInAndMemberIdIn(taskIds, List.of(memberId));
		validateTaskStatuses(taskIds, taskStatuses);
		request.tasks().forEach(req -> {
			taskStatuses.stream()
					.filter(taskStatus -> taskStatus.getTask().getId().equals(req.id()))
					.findFirst()
					.ifPresent(taskStatus -> taskStatus.setChecked(req.status()));
		});
		taskStatusRepository.saveAll(taskStatuses);
	}

	private void validateTaskStatuses(List<Long> taskIds, List<TaskStatus> taskStatuses) {
		List<Long> failedTaskIds = taskIds.stream()
				.filter(taskId -> taskStatuses.stream()
						.noneMatch(ts -> ts.getTask().getId().equals(taskId) && !ts.getIsChecked()))
				.toList();
		if (!failedTaskIds.isEmpty()) {
			throw new NotFoundException("투두가 존재하지 않거나 이미 완료된 투두입니다. : " + failedTaskIds, NOT_FOUND_TASK);
		}
	}
}