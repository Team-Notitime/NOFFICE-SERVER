package com.notitime.noffice.domain.fcmtoken.persistence;

import com.notitime.noffice.domain.FcmToken;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {
	Optional<FcmToken> findByToken(String token);

	List<FcmToken> findByMemberId(Long memberId);

	void deleteByToken(String value);

	@Query("SELECT f.token FROM FcmToken f WHERE f.member.id IN :newMemberIds")
	List<String> findAllTokenByMemberIdIn(List<Long> newMemberIds);
}
