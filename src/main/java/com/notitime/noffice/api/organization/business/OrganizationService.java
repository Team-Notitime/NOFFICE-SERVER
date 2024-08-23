package com.notitime.noffice.api.organization.business;

import static com.notitime.noffice.domain.JoinStatus.ACTIVE;
import static com.notitime.noffice.domain.OrganizationRole.LEADER;
import static com.notitime.noffice.domain.OrganizationRole.PARTICIPANT;
import static com.notitime.noffice.global.web.BusinessErrorCode.ALREADY_JOINED_ORGANIZATION;
import static com.notitime.noffice.global.web.BusinessErrorCode.NOT_FOUND_MEMBER;
import static com.notitime.noffice.global.web.BusinessErrorCode.NOT_FOUND_ORGANIZATION;

import com.notitime.noffice.api.category.presentation.dto.request.CategoryModifyRequest;
import com.notitime.noffice.api.category.presentation.dto.response.CategoryModifyResponse;
import com.notitime.noffice.api.category.presentation.dto.response.CategoryResponses;
import com.notitime.noffice.api.image.strategy.ImageRetrievalContext;
import com.notitime.noffice.api.member.presentation.dto.response.MemberInfoResponse;
import com.notitime.noffice.api.organization.presentation.dto.request.ChangeRoleRequest;
import com.notitime.noffice.api.organization.presentation.dto.request.OrganizationCreateRequest;
import com.notitime.noffice.api.organization.presentation.dto.response.OrganizationCreateResponse;
import com.notitime.noffice.api.organization.presentation.dto.response.OrganizationImageResponse;
import com.notitime.noffice.api.organization.presentation.dto.response.OrganizationInfoResponse;
import com.notitime.noffice.api.organization.presentation.dto.response.OrganizationJoinResponse;
import com.notitime.noffice.api.organization.presentation.dto.response.OrganizationResponse;
import com.notitime.noffice.api.organization.presentation.dto.response.OrganizationSignupResponse;
import com.notitime.noffice.domain.JoinStatus;
import com.notitime.noffice.domain.OrganizationRole;
import com.notitime.noffice.domain.category.model.Category;
import com.notitime.noffice.domain.category.persistence.CategoryRepository;
import com.notitime.noffice.domain.member.model.Member;
import com.notitime.noffice.domain.member.persistence.MemberRepository;
import com.notitime.noffice.domain.organization.model.Organization;
import com.notitime.noffice.domain.organization.model.OrganizationCategory;
import com.notitime.noffice.domain.organization.model.OrganizationMember;
import com.notitime.noffice.domain.organization.persistence.OrganizationMemberRepository;
import com.notitime.noffice.domain.organization.persistence.OrganizationRepository;
import com.notitime.noffice.global.exception.ForbiddenException;
import com.notitime.noffice.global.exception.NotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrganizationService {

	private final OrganizationMemberRepository organizationMemberRepository;
	private final OrganizationRepository organizationRepository;
	private final MemberRepository memberRepository;
	private final CategoryRepository categoryRepository;

	private final RoleVerifier roleVerifier;
	private final ImageRetrievalContext imageRetrievalContext;

	public OrganizationInfoResponse getInformation(Long memberId, Long organizationId) {
		Organization organization = getOrganizationEntity(organizationId);
		return OrganizationInfoResponse.of(
				roleVerifier.findRole(memberId, organizationId),
				organization,
				getCategoryNames(organization),
				getMemberCountByRole(organizationId, LEADER),
				getMemberCountByRole(organizationId, PARTICIPANT),
				isAnyMemberPending(organizationId));
	}

	public OrganizationSignupResponse getSignUpInfo(Long memberId, Long organizationId) {
		if (roleVerifier.isMemberInOrganization(memberId, organizationId)) {
			throw new ForbiddenException(ALREADY_JOINED_ORGANIZATION);
		}
		return OrganizationSignupResponse.of(getOrganizationEntity(organizationId));
	}

	public OrganizationCreateResponse create(Long createMemberId, OrganizationCreateRequest request) {
		return OrganizationCreateResponse.of(organizationRepository.save(createByRequest(createMemberId, request)));
	}

	public OrganizationJoinResponse join(Long memberId, Long organizationId) {
		roleVerifier.verifyJoinedMember(memberId, organizationId);
		Organization organization = getOrganizationEntity(organizationId);
		Member member = getMemberEntity(memberId);
		organizationMemberRepository.save(OrganizationMember.join(organization, member));
		return OrganizationJoinResponse.from(organization, member);
	}

	public Slice<OrganizationResponse> getJoined(Long memberId, Pageable pageable) {
		Slice<Organization> organizations = organizationMemberRepository.findOrganizationsByMemberId(memberId,
				pageable);
		List<OrganizationResponse> responses = organizations.stream()
				.map(organization -> {
					OrganizationMember joined = getJoinedMemberEntity(memberId, organization.getId());
					return OrganizationResponse.of(organization, joined.getRole(), joined.getStatus());
				})
				.toList();
		return new SliceImpl<>(responses, pageable, organizations.hasNext());
	}

	public CategoryModifyResponse modifyCategories(Long memberId, Long organizationId, CategoryModifyRequest request) {
		roleVerifier.verifyLeader(memberId, organizationId);
		Organization organization = getOrganizationEntity(organizationId);
		List<Category> categories = getCategoryList(request.categoryIds());
		organization.updateCategories(categories);
		return CategoryModifyResponse.of(organization, CategoryResponses.from(categories));
	}

	public void changeRoles(Long memberId, Long organizationId, ChangeRoleRequest request) {
		roleVerifier.verifyLeader(memberId, organizationId);
		roleVerifier.verifyMultipleMembers(organizationId, request.memberIds());
		organizationMemberRepository.bulkUpdateRole(organizationId, request.memberIds(), request.role());
	}

	public List<MemberInfoResponse> getPendingMembers(Long memberId, Long organizationId) {
		roleVerifier.verifyLeader(memberId, organizationId);
		return organizationMemberRepository.findPendingMembers(organizationId).stream()
				.map(MemberInfoResponse::from)
				.toList();
	}

	public void registerMember(Long memberId, Long organizationId, ChangeRoleRequest request) {
		roleVerifier.verifyLeader(memberId, organizationId);
		roleVerifier.verifyMultipleMembers(organizationId, request.memberIds());
		organizationMemberRepository.bulkUpdateStatus(organizationId, request.memberIds(), ACTIVE);
	}

	public OrganizationImageResponse getSelectableCover(Long memberId, Long organizationId) {
		roleVerifier.verifyLeader(memberId, organizationId);
		return OrganizationImageResponse.of(organizationId,
				imageRetrievalContext.retrieve(organizationId));
	}

	public void deleteProfileImage(Long organizationId) {
		Organization organization = getOrganizationEntity(organizationId);
		organization.deleteProfileImage();
		organizationRepository.save(organization);
	}

	private Member getMemberEntity(Long memberId) {
		return memberRepository.findById(memberId)
				.orElseThrow(() -> new NotFoundException(NOT_FOUND_MEMBER));
	}

	private Organization getOrganizationEntity(Long organizationId) {
		return organizationRepository.findById(organizationId)
				.orElseThrow(() -> new NotFoundException(NOT_FOUND_ORGANIZATION));
	}

	private OrganizationMember getJoinedMemberEntity(Long memberId, Long organizationId) {
		return organizationMemberRepository.findByOrganizationIdAndMemberId(organizationId, memberId)
				.orElseThrow(() -> new NotFoundException("요청한 멤버가 속한 조직이 없습니다.", NOT_FOUND_ORGANIZATION));
	}

	private List<Category> getCategoryList(List<Long> categoryIds) {
		return categoryRepository.findByIdIn(categoryIds);
	}

	private List<String> getCategoryNames(Organization organization) {
		return organization.getCategories().stream()
				.map(OrganizationCategory::getCategory)
				.map(Category::getName)
				.toList();
	}

	private Long getMemberCountByRole(Long organizationId, OrganizationRole role) {
		return organizationMemberRepository.countByOrganizationIdAndRole(organizationId, role);
	}

	private Boolean isAnyMemberPending(Long organizationId) {
		return organizationMemberRepository.existsByOrganizationIdAndStatus(organizationId, JoinStatus.PENDING);
	}

	private Organization createByRequest(Long createMemberId, OrganizationCreateRequest request) {
		List<Category> categories = categoryRepository.findByIdIn(request.categoryList());
		Member leader = getMemberEntity(createMemberId);
		return Organization.create(request.name(), request.endAt(), request.profileImage(), categories, leader);
	}
}
