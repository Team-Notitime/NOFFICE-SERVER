package com.notitime.noffice.domain.task.model;

import com.notitime.noffice.domain.BaseTimeEntity;
import com.notitime.noffice.domain.announcement.model.Announcement;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Task extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "announcement_id", nullable = false)
	private Announcement announcement;

	private Task(String content, Announcement announcement) {
		this.content = content;
		this.announcement = announcement;
	}

	public static Task create(String content, Announcement announcement) {
		Task task = new Task(content, announcement);
		task.setAnnouncement(announcement);
		return task;
	}

	public Task modify(String content) {
		this.content = content;
		return this;
	}

	public void setAnnouncement(Announcement announcement) {
		this.announcement = announcement;
		if (!announcement.getTasks().contains(this)) {
			announcement.getTasks().add(this);
		}
	}
}
