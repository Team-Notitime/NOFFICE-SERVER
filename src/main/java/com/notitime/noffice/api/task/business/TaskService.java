package com.notitime.noffice.api.task.business;

import static com.notitime.noffice.global.response.BusinessErrorCode.NOT_FOUND_ANNOUNCEMENT;
import static com.notitime.noffice.global.response.BusinessErrorCode.NOT_FOUND_TASK;

import com.notitime.noffice.domain.announcement.persistence.AnnouncementRepository;
import com.notitime.noffice.domain.organization.model.Organization;
import com.notitime.noffice.domain.organization.persistence.OrganizationMemberRepository;
import com.notitime.noffice.domain.task.model.Task;
import com.notitime.noffice.domain.task.model.TaskStatus;
import com.notitime.noffice.domain.task.persistence.TaskRepository;
import com.notitime.noffice.domain.task.persistence.TaskStatusRepository;
import com.notitime.noffice.global.exception.NotFoundException;
import com.notitime.noffice.request.TaskModifyRequest;
import com.notitime.noffice.response.AssignedTaskResponse;
import com.notitime.noffice.response.TaskModifyResponse;
import com.notitime.noffice.response.TaskResponse;
import com.notitime.noffice.response.TaskResponses;
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

	private final TaskStatusRepository taskStatusRepository;
	private final OrganizationMemberRepository organizationMemberRepository;
	private final TaskRepository taskRepository;
	private final AnnouncementRepository announcementRepository;

	public TaskModifyResponse modify(TaskModifyRequest taskModifyRequest) {
		Task task = findById(taskModifyRequest.id());
		return TaskModifyResponse.from(task.modify(taskModifyRequest.content()));
	}

	public Slice<AssignedTaskResponse> getAssignedTasks(Long memberId, Pageable pageable) {
		Slice<Organization> organizations = getSlicedOrganizations(memberId, pageable);
		List<AssignedTaskResponse> responses = organizations.stream()
				.map(organization -> searchAssignedTasks(memberId, organization))
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

	private Slice<Organization> getSlicedOrganizations(Long memberId, Pageable pageable) {
		return organizationMemberRepository.findOrganizationsByMemberId(memberId, pageable);
	}

	private Task findById(Long taskId) {
		return taskRepository.findById(taskId)
				.orElseThrow(() -> new NotFoundException(NOT_FOUND_TASK));
	}

	private AssignedTaskResponse searchAssignedTasks(Long memberId, Organization organization) {
		List<TaskResponse> tasks = TaskResponse.fromTaskStatus(getTaskStatuses(memberId, organization.getId()));
		return new AssignedTaskResponse(organization.getId(), organization.getName(), tasks);
	}

	private List<TaskStatus> getTaskStatuses(Long memberId, Long organizationId) {
		return taskStatusRepository.findTop5ByOrganizationIdAndMemberId(organizationId, memberId);
	}

	private void validateAnnouncement(Long announcementId) {
		if (!announcementRepository.existsById(announcementId)) {
			throw new NotFoundException(NOT_FOUND_ANNOUNCEMENT);
		}
	}
}