package com.notitime.noffice.domain.member.model;

import com.notitime.noffice.domain.BaseTimeEntity;
import com.notitime.noffice.domain.SocialAuthProvider;
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

	@Column(nullable = false)
	private String alias;

	@Column(nullable = false)
	private String serialId;

	private String profileImage;

	private String email;

	@Enumerated(EnumType.STRING)
	private SocialAuthProvider socialAuthProvider;

	@OneToMany(mappedBy = "member")
	private final List<OrganizationMember> organizations = new ArrayList<>();

	public static Member createAuthorizedMember(String serialId, final String name, final String email) {
		return Member.builder()
				.serialId(serialId)
				.name(name)
				.email(email)
				.build();
	}
}
