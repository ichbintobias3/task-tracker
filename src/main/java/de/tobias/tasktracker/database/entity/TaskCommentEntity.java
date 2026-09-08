package de.tobias.tasktracker.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "task_comment")
public class TaskCommentEntity {

	@Id
	private UUID id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_id")
	private AppUserEntity appUser;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "task_id")
	private TaskEntity task;

	@NotNull
	private String title;

	private String content;

	@NotNull
	@Column(name = "created_at")
	private Instant createdAt;

	@Column(name = "updated_at")
	private Instant updatedAt;

	public TaskCommentEntity() {
		this.id = UUID.randomUUID();
	}
}
