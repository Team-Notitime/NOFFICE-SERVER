package com.notitime.noffice.api.organization.presentation;

import com.notitime.noffice.api.announcement.presentation.dto.response.AnnouncementCoverResponse;
import com.notitime.noffice.api.category.presentation.dto.request.CategoryModifyRequest;
import com.notitime.noffice.api.category.presentation.dto.response.CategoryModifyResponse;
import com.notitime.noffice.api.member.presentation.dto.response.MemberInfoResponse;
import com.notitime.noffice.api.organization.presentation.dto.request.ChangeRoleRequest;
import com.notitime.noffice.api.organization.presentation.dto.request.OrganizationCreateRequest;
import com.notitime.noffice.api.organization.presentation.dto.response.OrganizationCreateResponse;
import com.notitime.noffice.api.organization.presentation.dto.response.OrganizationImageResponse;
import com.notitime.noffice.api.organization.presentation.dto.response.OrganizationInfoResponse;
import com.notitime.noffice.api.organization.presentation.dto.response.OrganizationJoinResponse;
import com.notitime.noffice.api.organization.presentation.dto.response.OrganizationMemberResponses;
import com.notitime.noffice.api.organization.presentation.dto.response.OrganizationResponse;
import com.notitime.noffice.api.organization.presentation.dto.response.OrganizationSignupResponse;
import com.notitime.noffice.auth.AuthMember;
import com.notitime.noffice.global.web.NofficeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "조직", description = "조직 관련 API")
interface OrganizationApi {

	@Operation(summary = "[인증] 단일 조직 정보 조회", description = "조직의 정보(조직명, 가입 대기여부, 가입자수) 를 조회합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "조직 정보 조회에 성공하였습니다."),
			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "404", description = "조직 정보가 없습니다."),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<OrganizationInfoResponse> getInformation(@Parameter(hidden = true) @AuthMember Long memberId,
	                                                         @PathVariable Long organizationId);

	@Operation(summary = "[인증] 조직 가입 정보 조회", description = "조직 가입을 위한 공개정보를 조회합니다. (조직명, 프로필 이미지 주소)", responses = {
			@ApiResponse(responseCode = "200", description = "조직 가입 정보 조회에 성공하였습니다."),
			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "404", description = "조직 정보가 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<OrganizationSignupResponse> getSignUpInfo(@Parameter(hidden = true) @AuthMember final Long memberId,
	                                                          @PathVariable final Long organizationId);

	@Operation(summary = "[인증] 조직 생성", description = "조직을 생성합니다.", responses = {
			@ApiResponse(responseCode = "201", description = "조직 생성에 성공하였습니다."),
			@ApiResponse(responseCode = "400", description = "조직 생성에 실패하였습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<OrganizationCreateResponse> create(@Parameter(hidden = true) @AuthMember final Long memberId,
	                                                   @RequestBody @Valid final OrganizationCreateRequest request);

	@Operation(summary = "[인증] 조직 가입", description = "조직에 가입합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "조직 가입에 성공하였습니다."),
			@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다. 토큰을 확인해주세요.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<OrganizationJoinResponse> join(@Parameter(hidden = true) @AuthMember final Long memberId,
	                                               @PathVariable Long organizationId);

	@Operation(summary = "[인증] 사용자의 가입된 조직 조회 (Paging)", description = "멤버가 가입한 조직 목록을 조회합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "회원의 가입된 조직 조회에 성공하였습니다."),
			@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다. 토큰을 확인해주세요.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "404", description = "가입된 조직이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<Slice<OrganizationResponse>> getJoined(@Parameter(hidden = true) @AuthMember final Long memberId,
	                                                       Pageable pageable);

	@Operation(summary = "[인증] 조직별 노티 페이징 조회 (Paging)", description = "조직별 노티를 페이징 조회합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "조직별 노티 페이징 조회 성공"),
			@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다. 토큰을 확인해주세요.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "404", description = "조직에 등록된 노티가 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<Slice<AnnouncementCoverResponse>> getPublishedAnnouncements(
			@Parameter(hidden = true) @AuthMember final Long memberId,
			@PathVariable final Long organizationId,
			Pageable pageable);

	@Operation(summary = "[인증] 조직 카테고리 수정", description = "조직에 등록된 카테고리를 수정합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "조직 카테고리 수정 성공"),
			@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다. 토큰을 확인해주세요.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "404", description = "조직에 등록된 카테고리가 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<CategoryModifyResponse> modifyCategories(@Parameter(hidden = true) @AuthMember final Long memberId,
	                                                         @PathVariable final Long organizationId,
	                                                         @RequestBody @Valid final CategoryModifyRequest request);

	@Operation(summary = "[인증] 조직원 권한 변경", description = "타겟에 해당하는 멤버의 조직 내 권한을 입력한 권한대로 변경합니다.", responses = {
			@ApiResponse(responseCode = "204", description = "권한 변경에 성공하였습니다."),
			@ApiResponse(responseCode = "400", description = "권한 변경에 실패하였습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다. 토큰을 확인해주세요.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "404", description = "조직원이 존재하지 않습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<Void> changeRoles(@Parameter(hidden = true) @AuthMember Long memberId,
	                                  @PathVariable Long organizationId,
	                                  @RequestBody @Valid final ChangeRoleRequest request);

	@Operation(summary = "[인증] 조직 가입 대기자 조회", description = "조직 가입 대기자를 조회합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "조직 가입 대기자 조회에 성공하였습니다."),
			@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다. 토큰을 확인해주세요.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "404", description = "조직 가입 대기자가 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<List<MemberInfoResponse>> getPendingMembers(
			@Parameter(hidden = true) @AuthMember final Long memberId,
			@PathVariable final Long organizationId);

	@Operation(summary = "[인증] 조직 가입 수락", description = "조직 가입대기 사용자 목록을 가입 처리합니다.", responses = {
			@ApiResponse(responseCode = "204", description = "조직 가입 승인 성공"),
			@ApiResponse(responseCode = "400", description = "조직 가입 수락 실패", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다. 토큰을 확인해주세요.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "404", description = "조직원이 존재하지 않습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<Void> registerMember(@Parameter(hidden = true) @AuthMember final Long memberId,
	                                     @PathVariable Long organizationId,
	                                     @RequestBody final ChangeRoleRequest request);

	@Operation(summary = "[인증] 선택 가능한 커버 이미지 조회", description = "공지 발행 시 선택 가능한 커버 이미지를 모두 조회합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "선택 가능한 공지 커버 이미지 목록 조회 성공"),
			@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다. 토큰을 확인해주세요.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "404", description = "공지 커버 이미지가 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<OrganizationImageResponse> getSelectableCover(
			@Parameter(hidden = true) @AuthMember final Long memberId,
			@PathVariable final Long organizationId);

	@Operation(summary = "[인증] 조직 프로필 이미지 삭제", description = "조직 프로필 이미지를 삭제합니다.", responses = {
			@ApiResponse(responseCode = "204", description = "프로필 이미지 삭제 성공"),
			@ApiResponse(responseCode = "400", description = "프로필 이미지 삭제 실패", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다. 토큰을 확인해주세요.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<Void> deleteProfileImage(@Parameter(hidden = true) @AuthMember final Long memberId,
	                                         @PathVariable Long organizationId);

	@Operation(summary = "[인증] 소속 조직원 권한별 전체 조회", description = "조직 내 소속원 전체를 조회합니다. LEADER, PARTICIPANT로 구분됩니다.", responses = {
			@ApiResponse(responseCode = "200", description = "조직 가입자 목록 조회 성공"),
			@ApiResponse(responseCode = "400", description = "프로필 이미지 삭제 실패", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다. 토큰을 확인해주세요.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "403", description = "요청을 수행할 수 있는 권한이 없습니다.", content = @Content(schema = @Schema(implementation = NofficeResponse.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러 발생", content = @Content(schema = @Schema(implementation = NofficeResponse.class)))
	})
	NofficeResponse<OrganizationMemberResponses> getRegisteredMembers(
			@Parameter(hidden = true) @AuthMember final Long memberId,
			@PathVariable Long organizationId);
}
