package com.notitime.noffice.presentation;

import com.notitime.noffice.global.response.BusinessSuccessCode;
import com.notitime.noffice.global.response.NofficeResponse;
import com.notitime.noffice.request.OrganizationCreateRequest;
import com.notitime.noffice.request.OrganizationJoinRequest;
import com.notitime.noffice.response.OrganizationJoinResponse;
import com.notitime.noffice.response.OrganizationResponse;
import com.notitime.noffice.response.OrganizationResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "조직", description = "조직 가입, 정보 조회 API")
@RestController
@RequestMapping("/v1/organization")
@RequiredArgsConstructor
public class OrganizationController {

	@Operation(summary = "단일 조직 정보 조회")
	@GetMapping("/{organizationId}")
	public NofficeResponse<OrganizationResponse> getOrganization(@PathVariable Long organizationId) {
		return NofficeResponse.success(BusinessSuccessCode.GET_ORGANIZATION_SUCCESS);
	}

	@Operation(summary = "조직 목록 조회")
	@GetMapping("/list")
	public NofficeResponse<OrganizationResponses> getOrganizationList() {
		return NofficeResponse.success(BusinessSuccessCode.GET_ORGANIZATION_SUCCESS);
	}

	@Operation(summary = "조직 생성")
	@PostMapping
	public NofficeResponse<OrganizationResponse> createOrganization(
			@RequestBody @Valid final OrganizationCreateRequest request) {
		return NofficeResponse.success(BusinessSuccessCode.POST_ORGANIZATION_SUCCESS);
	}

	@Operation(summary = "멤버의 조직 가입")
	@PostMapping("/{organizationId}/join")
	public NofficeResponse<OrganizationJoinResponse> joinOrganization(@PathVariable Long organizationId,
	                                                                  @RequestBody @Valid final OrganizationJoinRequest request) {
		return NofficeResponse.success(BusinessSuccessCode.POST_JOIN_ORGANIZATION_SUCCESS);
	}
}
