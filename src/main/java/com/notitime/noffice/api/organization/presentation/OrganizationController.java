package com.notitime.noffice.api.organization.presentation;

import static com.notitime.noffice.global.response.BusinessSuccessCode.CREATE_ORGANIZATION_SUCCESS;
import static com.notitime.noffice.global.response.BusinessSuccessCode.GET_JOINED_ORGANIZATIONS_SUCCESS;
import static com.notitime.noffice.global.response.BusinessSuccessCode.GET_ORGANIZATION_SUCCESS;
import static com.notitime.noffice.global.response.BusinessSuccessCode.GET_PUBLISHED_ANNOUNCEMENTS_SUCCESS;
import static com.notitime.noffice.global.response.BusinessSuccessCode.GET_SIGNUP_INFO_SUCCESS;
import static com.notitime.noffice.global.response.BusinessSuccessCode.PATCH_CHANGE_ROLES_SUCCESS;
import static com.notitime.noffice.global.response.BusinessSuccessCode.POST_JOIN_ORGANIZATION_SUCCESS;
import static com.notitime.noffice.global.response.BusinessSuccessCode.PUT_CATEGORIES_SUCCESS;

import com.notitime.noffice.api.announcement.business.AnnouncementService;
import com.notitime.noffice.api.announcement.presentation.dto.OrganizationCreateResponse;
import com.notitime.noffice.api.announcement.presentation.dto.OrganizationInfoResponse;
import com.notitime.noffice.api.announcement.presentation.dto.OrganizationJoinResponse;
import com.notitime.noffice.api.announcement.presentation.dto.OrganizationResponse;
import com.notitime.noffice.api.announcement.presentation.dto.OrganizationSignupResponse;
import com.notitime.noffice.api.organization.business.OrganizationService;
import com.notitime.noffice.auth.AuthMember;
import com.notitime.noffice.global.response.NofficeResponse;
import com.notitime.noffice.request.CategoryModifyRequest;
import com.notitime.noffice.request.OrganizationCreateRequest;
import com.notitime.noffice.response.AnnouncementCoverResponse;
import com.notitime.noffice.response.CategoryModifyResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationController implements OrganizationApi {

	private final OrganizationService organizationService;
	private final AnnouncementService announcementService;

	@GetMapping("/{organizationId}")
	public NofficeResponse<OrganizationInfoResponse> getInformation(@AuthMember final Long memberId,
	                                                                @PathVariable Long organizationId) {
		return NofficeResponse.success(GET_ORGANIZATION_SUCCESS,
				organizationService.getInformation(memberId, organizationId));
	}

	@GetMapping("/{organizationId}/signup-info")
	public NofficeResponse<OrganizationSignupResponse> getSignUpInfo(@AuthMember final Long memberId,
	                                                                 @PathVariable final Long organizationId) {
		return NofficeResponse.success(GET_SIGNUP_INFO_SUCCESS,
				organizationService.getSignUpInfo(memberId, organizationId));
	}

	@GetMapping
	public NofficeResponse<Slice<OrganizationResponse>> getJoined(@AuthMember final Long memberId, Pageable pageable) {
		return NofficeResponse.success(GET_JOINED_ORGANIZATIONS_SUCCESS,
				organizationService.getJoined(memberId, pageable));
	}

	@PostMapping
	public NofficeResponse<OrganizationCreateResponse> create(@AuthMember final Long memberId,
	                                                          @RequestBody @Valid final OrganizationCreateRequest request) {
		return NofficeResponse.success(CREATE_ORGANIZATION_SUCCESS,
				organizationService.create(memberId, request));
	}

	@PostMapping("/{organizationId}/join")
	public NofficeResponse<OrganizationJoinResponse> join(@AuthMember final Long memberId,
	                                                      @PathVariable Long organizationId) {
		return NofficeResponse.success(POST_JOIN_ORGANIZATION_SUCCESS,
				organizationService.join(memberId, organizationId));
	}

	@GetMapping("/{organizationId}/announcements")
	public NofficeResponse<Slice<AnnouncementCoverResponse>> getPublishedAnnouncements(
			@AuthMember final Long memberId, @PathVariable final Long organizationId, Pageable pageable) {
		return NofficeResponse.success(GET_PUBLISHED_ANNOUNCEMENTS_SUCCESS,
				announcementService.getPublishedAnnouncements(organizationId, pageable));
	}

	@PutMapping("/{organizationId}/categories")
	public NofficeResponse<CategoryModifyResponse> modifyCategories(@AuthMember final Long memberId,
	                                                                @PathVariable Long organizationId,
	                                                                @RequestBody @Valid final CategoryModifyRequest request) {
		return NofficeResponse.success(PUT_CATEGORIES_SUCCESS,
				organizationService.modifyCategories(memberId, organizationId, request));
	}

	@PatchMapping("/{organizationId}/roles")
	public NofficeResponse<Void> changeRoles(@AuthMember final Long memberId, @PathVariable Long organizationId,
	                                         @RequestBody @Valid final ChangeRoleRequest request) {
		organizationService.changeRoles(memberId, organizationId, request);
		return NofficeResponse.success(PATCH_CHANGE_ROLES_SUCCESS);
	}
}
