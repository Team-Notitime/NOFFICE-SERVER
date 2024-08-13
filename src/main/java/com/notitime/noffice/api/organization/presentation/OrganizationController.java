package com.notitime.noffice.api.organization.presentation;

import com.notitime.noffice.api.announcement.business.AnnouncementService;
import com.notitime.noffice.api.organization.business.OrganizationService;
import com.notitime.noffice.auth.LoginUser;
import com.notitime.noffice.global.response.BusinessSuccessCode;
import com.notitime.noffice.global.response.NofficeResponse;
import com.notitime.noffice.request.OrganizationCreateRequest;
import com.notitime.noffice.response.AnnouncementCoverResponse;
import com.notitime.noffice.response.OrganizationCreateResponse;
import com.notitime.noffice.response.OrganizationJoinResponse;
import com.notitime.noffice.response.OrganizationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationController implements OrganizationApi {

	private final OrganizationService organizationService;
	private final AnnouncementService announcementService;

	@GetMapping
	public NofficeResponse<Slice<OrganizationResponse>> getJoinedOrganizations(@LoginUser final Long memberId,
	                                                                           Pageable pageable) {
		return NofficeResponse.success(BusinessSuccessCode.GET_JOINED_ORGANIZATIONS_SUCCESS,
				organizationService.getOrganizationsByMemberId(memberId, pageable));
	}

	@GetMapping("/{organizationId}")
	public NofficeResponse<OrganizationResponse> getInformation(@LoginUser final Long memberId, @PathVariable Long organizationId) {
		return NofficeResponse.success(BusinessSuccessCode.GET_ORGANIZATION_SUCCESS, organizationService.getInformation(memberId, organizationId);
	}

	@PostMapping
	public NofficeResponse<OrganizationCreateResponse> createOrganization(@LoginUser final Long memberId,
	                                                                      @RequestBody @Valid final OrganizationCreateRequest request) {
		return NofficeResponse.success(BusinessSuccessCode.CREATE_ORGANIZATION_SUCCESS,
				organizationService.createOrganization(request));
	}

	@PostMapping("/{organizationId}/join")
	public NofficeResponse<OrganizationJoinResponse> joinOrganization(@LoginUser final Long memberId,
	                                                                  @PathVariable Long organizationId) {
		return NofficeResponse.success(BusinessSuccessCode.POST_JOIN_ORGANIZATION_SUCCESS,
				organizationService.joinOrganization(memberId, organizationId));
	}

	@GetMapping("/{organizationId}/announcements")
	public NofficeResponse<Slice<AnnouncementCoverResponse>> getPublishedAnnouncements(
			@LoginUser final Long memberId, @PathVariable final Long organizationId, Pageable pageable) {
		return NofficeResponse.success(BusinessSuccessCode.GET_PUBLISHED_ANNOUNCEMENTS_SUCCESS,
				announcementService.getPublishedAnnouncements(organizationId, pageable));
	}
}
