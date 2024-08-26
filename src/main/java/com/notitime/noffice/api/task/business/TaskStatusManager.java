package com.notitime.noffice.api.task.business;

import com.notitime.noffice.domain.JoinStatus;
import com.notitime.noffice.domain.announcement.model.Announcement;
import com.notitime.noffice.domain.member.model.Member;
import com.notitime.noffice.domain.organization.model.Organization;
import com.notitime.noffice.domain.organization.persistence.OrganizationMemberRepository;
import com.notitime.noffice.domain.organization.persistence.OrganizationRepository;
import com.notitime.noffice.domain.task.model.TaskStatus;
import com.notitime.noffice.domain.task.persistence.TaskRepository;
import com.notitime.noffice.domain.task.persistence.TaskStatusRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskStatusManager {
	private final TaskRepository taskRepository;
	private final TaskStatusRepository taskStatusRepository;
	private final OrganizationRepository organizationRepository;
	private final OrganizationMemberRepository organizationMemberRepository;

	public void assignTasks(Organization organization, Announcement announcement) {
		List<Member> activeMembers = organizationMemberRepository.findMembersByOrganizationIdAndStatus(
				organization.getId(),
				JoinStatus.ACTIVE);
		List<TaskStatus> taskStatuses = activeMembers.stream()
				.flatMap(member -> announcement.getTasks().stream()
						.map(task -> TaskStatus.create(task, member)))
				.toList();
		taskStatusRepository.saveAll(taskStatuses);
	}

//	public void recordTaskStatus(Long memberId, Long taskId) {
//		TaskStatus taskStatus = taskStatusRepository.findByTaskIdAndMemberId(taskId, memberId)
//				.orElseThrow(() -> new NotFoundException(NOT_FOUND_TASK_STATUS));
//		taskStatus.check();
//	}
}
