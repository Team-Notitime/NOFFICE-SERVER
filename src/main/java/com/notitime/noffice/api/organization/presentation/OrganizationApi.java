package com.notitime.noffice.api.organization.presentation;

import com.notitime.noffice.auth.LoginUser;
import com.notitime.noffice.global.response.NofficeResponse;
import com.notitime.noffice.request.OrganizationCreateRequest;
import com.notitime.noffice.response.AnnouncementCoverResponse;
import com.notitime.noffice.response.OrganizationCreateResponse;
import com.notitime.noffice.response.OrganizationInfoResponse;
import com.notitime.noffice.response.OrganizationJoinResponse;
import com.notitime.noffice.response.OrganizationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "조직", description = "조직 관련 API")
public interface OrganizationApi {

	@Operation(summary = "단일 조직 정보 조회", description = "조직의 정보(조직명, 가입 대기여부, 가입자수) 를 조회합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "조직 정보 조회에 성공하였습니다."),
			@ApiResponse(responseCode = "404", description = "조직 정보가 없습니다.")
	})
	NofficeResponse<OrganizationInfoResponse> getInformation(@LoginUser Long memberId,
	                                                         @PathVariable Long organizationId);

	@Operation(summary = "조직 생성", description = "조직을 생성합니다.", responses = {
			@ApiResponse(responseCode = "201", description = "조직 생성에 성공하였습니다."),
			@ApiResponse(responseCode = "400", description = "조직 생성에 실패하였습니다.")
	})
	NofficeResponse<OrganizationCreateResponse> createOrganization(@LoginUser Long memberId,
	                                                               @RequestBody @Valid final OrganizationCreateRequest request);

	@Operation(summary = "조직 가입", description = "조직에 가입합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "조직 가입에 성공하였습니다.")
	})
	NofficeResponse<OrganizationJoinResponse> joinOrganization(@LoginUser Long memberId,
	                                                           @PathVariable Long organizationId);

	@Operation(summary = "사용자의 가입된 조직 페이징 조회", description = "멤버가 가입한 조직 목록을 조회합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "회원의 가입된 조직 조회에 성공하였습니다."),
			@ApiResponse(responseCode = "404", description = "가입된 조직이 없습니다.")
	})
	NofficeResponse<Slice<OrganizationResponse>> getJoinedOrganizations(@LoginUser Long memberId,
	                                                                    Pageable pageable);

	@Operation(summary = "조직별 노티 페이징 조회", description = "조직별 노티를 페이징 조회합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "조직별 노티 페이징 조회 성공"),
			@ApiResponse(responseCode = "404", description = "조직에 등록된 노티가 없습니다.")
	})
	NofficeResponse<Slice<AnnouncementCoverResponse>> getPublishedAnnouncements(@LoginUser final Long memberId,
	                                                                            @PathVariable final Long organizationId,
	                                                                            Pageable pageable);
}
