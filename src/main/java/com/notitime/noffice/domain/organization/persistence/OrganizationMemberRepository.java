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
import org.springframework.data.jpa.repository.Modifying;
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

	@Modifying
	@Query("UPDATE OrganizationMember om SET om.role = :role WHERE om.organization.id = :organizationId AND om.member.id IN :memberIds")
	int bulkUpdateRole(@Param("organizationId") Long organizationId, @Param("memberIds") List<Long> memberIds,
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

	@Query("SELECT om.member.id FROM OrganizationMember om WHERE om.organization.id = :organizationId AND om.member.id IN :memberIds AND om.status = 'ACTIVE'")
	List<Long> findActiveMemberIdsByOrganizationId(@Param("organizationId") Long organizationId,
	                                               @Param("memberIds") List<Long> memberIds);

}
