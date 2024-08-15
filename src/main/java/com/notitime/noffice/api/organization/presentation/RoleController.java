package com.notitime.noffice.api.organization.presentation;

import com.notitime.noffice.auth.AuthMember;
import com.notitime.noffice.global.response.BusinessSuccessCode;
import com.notitime.noffice.global.response.NofficeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleController implements RoleApi {

	private OrganizationRoleManager roleManager;

	@PatchMapping
	public NofficeResponse<Void> changeRoles(@AuthMember Long memberId,
	                                         @RequestBody ChangeRoleRequest request) {
		roleManager.changeRoles(memberId, request);
		return NofficeResponse.success(BusinessSuccessCode.PATCH_CHANGE_ROLES_SUCCESS);
	}
}
