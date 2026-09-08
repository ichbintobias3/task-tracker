package de.tobias.tasktracker.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "task")
public class TaskEntity {

	@Id
	private UUID id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	private ProjectEntity project;

	@NotNull
	private String name;

	@NotNull
	private String status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_id")
	private AppUserEntity user;

	private String description;

	@NotNull
	@Column(name = "created_at")
	private Instant createdAt;

	@Column(name = "updated_at")
	private Instant updatedAt;

	public TaskEntity() {
		this.id = UUID.randomUUID();
	}
}

