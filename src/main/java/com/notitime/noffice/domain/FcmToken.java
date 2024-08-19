package com.notitime.noffice.domain;

import com.notitime.noffice.domain.member.model.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class FcmToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	private String value;

	private String deviceId;

	private LocalDateTime expiredAt;

	public static FcmToken of(Member member, String value) {
		return new FcmToken(null, member, value, null, null);
	}

	private void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	private void setExpiredAt(LocalDateTime expiredAt) {
		this.expiredAt = expiredAt;
	}
}
