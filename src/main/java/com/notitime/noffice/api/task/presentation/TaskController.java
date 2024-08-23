package com.notitime.noffice.api.task.presentation;

import static com.notitime.noffice.global.web.BusinessSuccessCode.GET_ASSIGNED_TASKS_SUCCESS;
import static com.notitime.noffice.global.web.BusinessSuccessCode.PATCH_TASK_MODIFY_SUCCESS;

import com.notitime.noffice.api.task.business.TaskService;
import com.notitime.noffice.api.task.presentation.dto.request.TaskModifyRequest;
import com.notitime.noffice.api.task.presentation.dto.response.AssignedTaskResponse;
import com.notitime.noffice.api.task.presentation.dto.response.TaskModifyResponse;
import com.notitime.noffice.auth.AuthMember;
import com.notitime.noffice.global.web.NofficeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController implements TaskApi {

	private final TaskService taskService;

	@PatchMapping
	public NofficeResponse<TaskModifyResponse> modify(TaskModifyRequest taskModifyRequest) {
		return NofficeResponse.success(PATCH_TASK_MODIFY_SUCCESS, taskService.modify(taskModifyRequest));
	}

	@GetMapping("/assigned")
	public NofficeResponse<Slice<AssignedTaskResponse>> getAssigned(@AuthMember final Long memberId,
	                                                                @PageableDefault(size = 5)
	                                                                @SortDefault(sort = "id", direction = Sort.Direction.DESC)
	                                                                Pageable pageable) {
		return NofficeResponse.success(GET_ASSIGNED_TASKS_SUCCESS,
				taskService.getAssignedTasks(memberId, pageable));
	}
}
