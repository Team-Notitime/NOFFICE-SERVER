package com.notitime.noffice.domain.member.persistence;

import com.notitime.noffice.domain.member.model.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findBySerialId(String serialId);

	Boolean existsBySerialId(String serialId);
}
