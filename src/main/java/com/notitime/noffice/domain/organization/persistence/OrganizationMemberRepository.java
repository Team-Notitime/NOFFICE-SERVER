package com.notitime.noffice.domain.organization.persistence;

import com.notitime.noffice.domain.organization.model.OrganizationMember;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationMemberRepository extends JpaRepository<OrganizationMember, Long> {
	List<OrganizationMember> findByMemberId(Long memberId);
}
