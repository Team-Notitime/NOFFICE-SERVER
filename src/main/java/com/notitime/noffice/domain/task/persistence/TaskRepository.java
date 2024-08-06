package com.notitime.noffice.domain.task.persistence;

import com.notitime.noffice.domain.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
