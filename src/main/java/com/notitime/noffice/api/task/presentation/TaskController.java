package com.notitime.noffice.api.task.presentation;

import com.notitime.noffice.api.task.business.TaskService;
import com.notitime.noffice.auth.AuthMember;
import com.notitime.noffice.global.response.BusinessSuccessCode;
import com.notitime.noffice.global.response.NofficeResponse;
import com.notitime.noffice.request.TaskModifyRequest;
import com.notitime.noffice.response.AssignedTaskResponse;
import com.notitime.noffice.response.TaskModifyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController implements TaskApi {

	private final TaskService taskService;

	@PostMapping
	public NofficeResponse<TaskModifyResponse> modify(TaskModifyRequest taskModifyRequest) {
		return NofficeResponse.success(BusinessSuccessCode.OK, taskService.modify(taskModifyRequest));
	}

	@DeleteMapping("/{taskId}")
	public NofficeResponse<Void> delete(@PathVariable Long taskId) {
		taskService.delete(taskId);
		return NofficeResponse.success(BusinessSuccessCode.DELETE_TASK_SUCCESS);
	}

	@GetMapping("/assigned")
	public NofficeResponse<Slice<AssignedTaskResponse>> getAssigned(@AuthMember Long memberId, Pageable pageable) {
		return NofficeResponse.success(BusinessSuccessCode.GET_ASSIGNED_TASKS_SUCCESS,
				taskService.getAssignedTasks(memberId, pageable));
	}
}
