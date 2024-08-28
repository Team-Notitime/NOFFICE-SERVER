package com.notitime.noffice.domain.fcmtoken.persistence;

import com.notitime.noffice.domain.FcmToken;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {

	List<FcmToken> findByMemberId(Long memberId);

	@Query("SELECT f.token FROM FcmToken f WHERE f.member.id IN :newMemberIds")
	List<String> findAllTokenByMemberIdIn(List<Long> newMemberIds);

	@Modifying
	@Query("DELETE FROM FcmToken f WHERE f.member.id = :memberId AND f.token = :token")
	void deleteByMemberIdAndToken(Long memberId, String token);

	void deleteByMemberId(Long memberId);
}
