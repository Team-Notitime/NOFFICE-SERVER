package com.notitime.noffice.api.organization.business;

import static com.notitime.noffice.domain.OrganizationRole.LEADER;
import static com.notitime.noffice.domain.OrganizationRole.PARTICIPANT;

import com.notitime.noffice.api.announcement.presentation.dto.OrganizationCreateResponse;
import com.notitime.noffice.api.announcement.presentation.dto.OrganizationInfoResponse;
import com.notitime.noffice.api.announcement.presentation.dto.OrganizationJoinResponse;
import com.notitime.noffice.api.announcement.presentation.dto.OrganizationResponse;
import com.notitime.noffice.api.announcement.presentation.dto.OrganizationSignupResponse;
import com.notitime.noffice.api.organization.presentation.ChangeRoleRequest;
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
import com.notitime.noffice.global.exception.NotFoundException;
import com.notitime.noffice.global.response.BusinessErrorCode;
import com.notitime.noffice.request.CategoryModifyRequest;
import com.notitime.noffice.request.OrganizationCreateRequest;
import com.notitime.noffice.response.CategoryModifyResponse;
import com.notitime.noffice.response.CategoryResponses;
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

	public OrganizationSignupResponse getSignUpInfo(Long organizationId) {
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
					OrganizationRole role = roleVerifier.findRole(memberId, organization.getId());
					return OrganizationResponse.of(role, organization);
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

	private Member getMemberEntity(Long memberId) {
		return memberRepository.findById(memberId)
				.orElseThrow(() -> new NotFoundException(BusinessErrorCode.NOT_FOUND_MEMBER));
	}

	private Organization getOrganizationEntity(Long organizationId) {
		return organizationRepository.findById(organizationId)
				.orElseThrow(() -> new NotFoundException(BusinessErrorCode.NOT_FOUND_ORGANIZATION));
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
