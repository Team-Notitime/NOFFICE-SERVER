package com.notitime.noffice.domain.organization.persistence;

import static com.notitime.noffice.domain.OrganizationRole.LEADER;
import static com.notitime.noffice.domain.OrganizationRole.PARTICIPANT;

import com.notitime.noffice.domain.JoinStatus;
import com.notitime.noffice.domain.OrganizationRole;
import com.notitime.noffice.domain.member.model.Member;
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

	@Query("SELECT om.member FROM OrganizationMember om WHERE om.organization.id = :organizationId")
	Slice<Member> findMembersByOrganizationId(@Param("organizationId") Long organizationId, Pageable pageable);

	@Query("SELECT om.member FROM OrganizationMember om WHERE om.organization.id = :organizationId AND om.role = :role")
	List<Member> findMembersByOrganizationIdAndRole(@Param("organizationId") Long organizationId,
	                                                @Param("role") OrganizationRole role);

	default List<Member> findLeadersByOrganizationId(Long organizationId) {
		return findMembersByOrganizationIdAndRole(organizationId, LEADER);
	}

	default List<Member> findParticipantsByOrganizationId(Long organizationId) {
		return findMembersByOrganizationIdAndRole(organizationId, PARTICIPANT);
	}

	boolean existsByMemberIdAndOrganizationIdAndRole(Long memberId, Long organizationId,
	                                                 OrganizationRole organizationRole);

	boolean existsByMemberIdAndOrganizationIdAndStatus(Long memberId, Long organizationId, JoinStatus status);

	boolean existsByMemberIdAndOrganizationIdAndRoleAndStatus(Long memberId, Long organizationId, OrganizationRole role,
	                                                          JoinStatus status);

	Long countByOrganizationIdAndRole(Long organizationId, OrganizationRole role);

	boolean existsByOrganizationIdAndStatus(Long organizationId, JoinStatus status);
}
