package com.notitime.noffice.api.task.presentation;

import com.notitime.noffice.api.task.business.TaskService;
import com.notitime.noffice.auth.LoginUser;
import com.notitime.noffice.global.response.BusinessSuccessCode;
import com.notitime.noffice.global.response.NofficeResponse;
import com.notitime.noffice.request.TaskBulkCreateRequest;
import com.notitime.noffice.request.TaskCreateRequest;
import com.notitime.noffice.response.AssignedTaskResponse;
import com.notitime.noffice.response.TaskCreateResponse;
import com.notitime.noffice.response.TaskCreateResponses;
import com.notitime.noffice.response.TaskResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController implements TaskApi {

	private final TaskService taskService;

	@GetMapping
	public NofficeResponse<TaskResponses> getTasks() {
		return NofficeResponse.success(BusinessSuccessCode.OK);
	}

	@PostMapping
	public NofficeResponse<TaskCreateResponse> createTask(TaskCreateRequest taskCreateRequest) {
		return NofficeResponse.success(BusinessSuccessCode.OK);

	}

	@PostMapping("/bulk")
	public NofficeResponse<TaskCreateResponses> createBulkTask(TaskBulkCreateRequest taskBulkCreateRequest) {
		return NofficeResponse.success(BusinessSuccessCode.OK);

	}

	@DeleteMapping("/{taskId}")
	public NofficeResponse<Void> deleteTask(Long taskId) {
		return NofficeResponse.success(BusinessSuccessCode.OK);
	}

	@GetMapping("/assigned")
	public NofficeResponse<Slice<AssignedTaskResponse>> getAssignedTasks(@LoginUser Long memberId, Pageable pageable) {
		return NofficeResponse.success(BusinessSuccessCode.GET_ASSIGNED_TASKS_SUCCESS,
				taskService.getAssignedTasks(memberId, pageable));
	}
}
