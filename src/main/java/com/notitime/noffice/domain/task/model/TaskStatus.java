package com.notitime.noffice.domain.task.model;

import com.notitime.noffice.domain.BaseTimeEntity;
import com.notitime.noffice.domain.member.model.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TaskStatus extends BaseTimeEntity {
	@Id
	@GeneratedValue
	private Long id;

	private Boolean isChecked;

	private LocalDateTime checkedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "task_id")
	private Task task;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
}
