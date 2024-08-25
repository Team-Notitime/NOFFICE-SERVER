package com.notitime.noffice.domain.task.model;

import com.notitime.noffice.domain.BaseTimeEntity;
import com.notitime.noffice.domain.member.model.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;

@Entity
@Getter
public class TaskStatus extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Boolean isChecked;

	private LocalDateTime checkedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "task_id")
	private Task task;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	protected TaskStatus() {
		this.isChecked = false;
		this.checkedAt = null;
	}

	public static TaskStatus create(Task task, Member member) {
		TaskStatus taskStatus = new TaskStatus();
		taskStatus.setTask(task);
		taskStatus.setMember(member);
		return taskStatus;
	}

	private void setTask(Task task) {
		this.task = task;
	}

	private void setMember(Member member) {
		this.member = member;
	}

	public void setChecked(boolean status) {
		this.checkedAt = LocalDateTime.now();
		this.isChecked = status;
	}
}
