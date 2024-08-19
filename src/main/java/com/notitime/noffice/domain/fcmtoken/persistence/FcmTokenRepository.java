package com.notitime.noffice.domain.fcmtoken.persistence;

import com.notitime.noffice.domain.FcmToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {
	Optional<FcmToken> findByToken(String token);
}
