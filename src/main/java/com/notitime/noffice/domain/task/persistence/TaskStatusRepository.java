package com.notitime.noffice.domain.task.persistence;

import com.notitime.noffice.domain.task.model.TaskStatus;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {
	@Query("SELECT ts FROM TaskStatus ts WHERE ts.task.id = :taskId AND ts.member.id = :memberId")
	TaskStatus findByTaskIdAndMemberId(Long taskId, Long memberId);

	List<TaskStatus> findByTaskIdInAndMemberIdIn(Collection<Long> taskIds, Collection<Long> memberIds);
}
