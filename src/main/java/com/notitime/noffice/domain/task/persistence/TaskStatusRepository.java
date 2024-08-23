package com.notitime.noffice.domain.task.persistence;

import com.notitime.noffice.domain.task.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {
}
