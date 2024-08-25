package com.notitime.noffice.api.task.business;

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
		List<Member> members = getParticipants(organization.getId());
		List<Member> leaders = getLeaders(organization.getId());
		members.addAll(leaders);
		List<TaskStatus> taskStatuses = members.stream()
				.flatMap(member -> announcement.getTasks().stream()
						.map(task -> TaskStatus.create(task, member)))
				.toList();
		taskStatusRepository.saveAll(taskStatuses);
	}

	private List<Member> getLeaders(Long organizationId) {
		return organizationMemberRepository.findLeadersByOrganizationId(organizationId);
	}

	private List<Member> getParticipants(Long organizationId) {
		return organizationMemberRepository.findParticipantsByOrganizationId(organizationId);
	}

//	public void recordTaskStatus(Long memberId, Long taskId) {
//		TaskStatus taskStatus = taskStatusRepository.findByTaskIdAndMemberId(taskId, memberId)
//				.orElseThrow(() -> new NotFoundException(NOT_FOUND_TASK_STATUS));
//		taskStatus.check();
//	}
}
