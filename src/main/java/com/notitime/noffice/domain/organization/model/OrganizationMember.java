package com.notitime.noffice.domain.organization.model;

import static com.notitime.noffice.domain.JoinStatus.ACTIVE;
import static com.notitime.noffice.domain.JoinStatus.PENDING;
import static com.notitime.noffice.domain.OrganizationRole.LEADER;
import static com.notitime.noffice.domain.OrganizationRole.PARTICIPANT;

import com.notitime.noffice.domain.BaseTimeEntity;
import com.notitime.noffice.domain.JoinStatus;
import com.notitime.noffice.domain.OrganizationRole;
import com.notitime.noffice.domain.member.model.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class OrganizationMember extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private OrganizationRole role;

	@Enumerated(EnumType.STRING)
	private JoinStatus status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "organization_id")
	private Organization organization;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	public static OrganizationMember join(Organization organization, Member member) {
		OrganizationMember organizationMember = new OrganizationMember(null, PARTICIPANT, PENDING,
				organization, member);
		organizationMember.setOrganization(organization);
		organizationMember.setMember(member);
		return organizationMember;
	}

	public static OrganizationMember create(Organization organization, Member member) {
		return new OrganizationMember(null, LEADER, ACTIVE, organization, member);
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
		if (!organization.getMembers().contains(this)) {
			organization.getMembers().add(this);
		}
	}

	public void setMember(Member member) {
		this.member = member;
		if (!member.getOrganizations().contains(this)) {
			member.getOrganizations().add(this);
		}
	}
}
