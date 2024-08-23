package com.notitime.noffice.api.task.presentation.dto.response;

import com.notitime.noffice.domain.organization.model.Organization;
import java.util.List;

public record AssignedTaskResponse(
		Long organizationId,
		String organizationName,
		List<TaskResponse> tasks
) {
	public static AssignedTaskResponse from(Organization organization, List<TaskResponse> tasks) {
		return new AssignedTaskResponse(organization.getId(), organization.getName(), tasks);
	}
}
