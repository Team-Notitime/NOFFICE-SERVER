package com.notitime.noffice.api.task.business;

import static com.notitime.noffice.global.web.BusinessErrorCode.NOT_FOUND_ANNOUNCEMENT;
import static com.notitime.noffice.global.web.BusinessErrorCode.NOT_FOUND_TASK;

import com.notitime.noffice.api.task.presentation.dto.request.TaskModifyRequest;
import com.notitime.noffice.api.task.presentation.dto.response.AssignedTaskResponse;
import com.notitime.noffice.api.task.presentation.dto.response.TaskModifyResponse;
import com.notitime.noffice.api.task.presentation.dto.response.TaskResponse;
import com.notitime.noffice.api.task.presentation.dto.response.TaskResponses;
import com.notitime.noffice.domain.announcement.persistence.AnnouncementRepository;
import com.notitime.noffice.domain.member.persistence.MemberRepository;
import com.notitime.noffice.domain.organization.model.Organization;
import com.notitime.noffice.domain.organization.persistence.OrganizationMemberRepository;
import com.notitime.noffice.domain.task.model.Task;
import com.notitime.noffice.domain.task.persistence.TaskRepository;
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

	public TaskModifyResponse modify(TaskModifyRequest taskModifyRequest) {
		Task task = findById(taskModifyRequest.id());
		return TaskModifyResponse.from(task.modify(taskModifyRequest.content()));
	}

	public Slice<AssignedTaskResponse> getAssignedTasks(Long memberId, Pageable pageable) {
		Slice<Organization> organizations = getSlicedOrganizations(memberId, pageable); // 사용자가 가입한 조직의 페이징된 목록
		List<AssignedTaskResponse> responses = organizations.stream()
				.map(this::assembleTasksByOrganization)
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

	private AssignedTaskResponse assembleTasksByOrganization(Organization organization) {
		List<TaskResponse> taskResponses = getTop5LatestTask(organization);
		return AssignedTaskResponse.from(organization, taskResponses);
	}

	private List<TaskResponse> getTop5LatestTask(Organization organization) {
		return organization.getAnnouncements().stream()
				.flatMap(announcement -> announcement.getTasks().stream()
						.sorted(Comparator.comparing(Task::getCreatedAt).reversed())
						.limit(5))
				.map(TaskResponse::from)
				.toList();
	}
}