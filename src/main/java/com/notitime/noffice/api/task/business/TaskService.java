package com.notitime.noffice.api.task.business;

import com.notitime.noffice.domain.organization.model.Organization;
import com.notitime.noffice.domain.organization.persistence.OrganizationMemberRepository;
import com.notitime.noffice.domain.task.model.TaskStatus;
import com.notitime.noffice.domain.task.persistence.TaskStatusRepository;
import com.notitime.noffice.response.AssignedTaskResponse;
import com.notitime.noffice.response.TaskResponse;
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

	public Slice<AssignedTaskResponse> getAssignedTasks(Long memberId, Pageable pageable) {
		Slice<Organization> organizations = getSlicedOrganizations(memberId, pageable);
		List<AssignedTaskResponse> responses = organizations.stream()
				.map(organization -> searchAssignedTasks(memberId, organization))
				.toList();
		return new PageImpl<>(responses, pageable, organizations.getSize());
	}

	private Slice<Organization> getSlicedOrganizations(Long memberId, Pageable pageable) {
		return organizationMemberRepository.findOrganizationsByMemberId(memberId, pageable);
	}

	private AssignedTaskResponse searchAssignedTasks(Long memberId, Organization organization) {
		List<TaskResponse> tasks = TaskResponse.fromTaskStatus(getTaskStatuses(memberId, organization.getId()));
		return new AssignedTaskResponse(organization.getId(), organization.getName(), tasks);
	}

	private List<TaskStatus> getTaskStatuses(Long memberId, Long organizationId) {
		return taskStatusRepository.findTop5ByOrganizationIdAndMemberId(organizationId, memberId);
	}
}