package com.notitime.noffice.domain.organization.persistence;

import com.notitime.noffice.domain.organization.model.Organization;
import com.notitime.noffice.domain.organization.model.OrganizationMember;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrganizationMemberRepository extends JpaRepository<OrganizationMember, Long> {
	List<OrganizationMember> findByMemberId(Long memberId);

	@Query("SELECT om.organization FROM OrganizationMember om WHERE om.member.id = :memberId")
	Slice<Organization> findOrganizationsByMemberId(@Param("memberId") Long memberId, Pageable pageable);
}
