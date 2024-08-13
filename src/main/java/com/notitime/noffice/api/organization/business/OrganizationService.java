package com.notitime.noffice.api.organization.business;

import com.notitime.noffice.api.OrganizationRoleVerifier;
import com.notitime.noffice.domain.category.model.Category;
import com.notitime.noffice.domain.category.persistence.CategoryRepository;
import com.notitime.noffice.domain.member.model.Member;
import com.notitime.noffice.domain.member.persistence.MemberRepository;
import com.notitime.noffice.domain.organization.model.Organization;
import com.notitime.noffice.domain.organization.model.OrganizationMember;
import com.notitime.noffice.domain.organization.persistence.OrganizationMemberRepository;
import com.notitime.noffice.domain.organization.persistence.OrganizationRepository;
import com.notitime.noffice.global.exception.NotFoundException;
import com.notitime.noffice.global.response.BusinessErrorCode;
import com.notitime.noffice.request.OrganizationCreateRequest;
import com.notitime.noffice.response.OrganizationCreateResponse;
import com.notitime.noffice.response.OrganizationJoinResponse;
import com.notitime.noffice.response.OrganizationResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

	private final OrganizationRoleVerifier organizationRoleVerifier;

	public OrganizationResponse getOrganization(Long organizationId) {
		return OrganizationResponse.of(getOrganizationEntity(organizationId));
	}

	public Slice<OrganizationResponse> getOrganizationsByMemberId(Long memberId, Pageable pageable) {
		Slice<Organization> organizations = organizationMemberRepository.findOrganizationsByMemberId(memberId,
				pageable);
		List<OrganizationResponse> responses = organizations.stream()
				.map(OrganizationResponse::of)
				.toList();
		return new PageImpl<>(responses, pageable, responses.size());
	}

	public OrganizationCreateResponse createOrganization(OrganizationCreateRequest request) {
		List<Category> categories = categoryRepository.findByIdIn(request.categoryList());
		Organization organization = Organization.builder()
				.name(request.name())
				.endAt(request.endAt())
				.profileImage(request.profileImage())
				.build()
				.addCategories(categories);
		return OrganizationCreateResponse.of(organizationRepository.save(organization));
	}

	public OrganizationJoinResponse joinOrganization(Long memberId, Long organizationId) {
		organizationRoleVerifier.verifyJoinedMember(memberId, organizationId);
		Organization organization = getOrganizationEntity(organizationId);
		Member member = getMemberEntity(memberId);
		organizationMemberRepository.save(new OrganizationMember(organization, member));
		return OrganizationJoinResponse.from(organization, member);
	}

	private Member getMemberEntity(Long memberId) {
		return memberRepository.findById(memberId)
				.orElseThrow(() -> new NotFoundException(BusinessErrorCode.NOT_FOUND_MEMBER));
	}

	private Organization getOrganizationEntity(Long organizationId) {
		return organizationRepository.findById(organizationId)
				.orElseThrow(() -> new NotFoundException(BusinessErrorCode.NOT_FOUND_ORGANIZATION));
	}
}
