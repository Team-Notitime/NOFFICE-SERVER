package com.notitime.noffice.domain.member.model;

import com.notitime.noffice.domain.BaseTimeEntity;
import com.notitime.noffice.domain.FcmToken;
import com.notitime.noffice.domain.JoinStatus;
import com.notitime.noffice.domain.SocialAuthProvider;
import com.notitime.noffice.domain.organization.model.Organization;
import com.notitime.noffice.domain.organization.model.OrganizationMember;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	private String alias;

	private String serialId;

	@Column(columnDefinition = "TEXT")
	private String profileImage;

	@Column(nullable = false)
	private String email;

	@Enumerated(EnumType.STRING)
	private SocialAuthProvider socialAuthProvider;

	@OneToMany(mappedBy = "member")
	private final List<OrganizationMember> organizations = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private final List<FcmToken> fcmTokens = new ArrayList<>();

	public static Member createAuthorizedMember(
			final String serialId,
			final String name,
			final String email,
			final SocialAuthProvider socialAuthProvider,
			final String profileImage) {
		return Member.builder()
				.serialId(serialId)
				.name(name)
				.alias(name)
				.email(email)
				.socialAuthProvider(socialAuthProvider)
				.profileImage(profileImage)
				.build();
	}

	public void addFcmToken(FcmToken fcmToken) {
		this.fcmTokens.add(fcmToken);
	}

	public List<Organization> getOrganizationsByStatus(JoinStatus joinStatus) {
		return organizations.stream()
				.filter(organizationMember -> organizationMember.getStatus() == joinStatus)
				.map(OrganizationMember::getOrganization)
				.toList();
	}

	public void deleteProfileImage() {
		this.profileImage = null;
	}

	public void updateAlias(String alias) {
		this.alias = alias;
	}
}
