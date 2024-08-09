package com.notitime.noffice.domain.task.persistence;

import com.notitime.noffice.domain.task.model.TaskStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {
	@Query("SELECT ts FROM TaskStatus ts " +
			"JOIN FETCH ts.task t " +
			"WHERE ts.member.id = :memberId " +
			"AND ts.isChecked = false " +
			"AND t.announcement.organization.id = :organizationId " +
			"ORDER BY t.createdAt DESC")
	List<TaskStatus> findTop5ByOrganizationIdAndMemberId(Long organizationId, Long memberId);
}
