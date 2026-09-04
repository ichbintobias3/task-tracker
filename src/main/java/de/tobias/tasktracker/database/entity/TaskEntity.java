package de.tobias.tasktracker.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name = "task")
public class TaskEntity {

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "project_id")
	private ProjectEntity project;

	@NotNull
	private String name;

	@NotNull
	private String status;

	@ManyToOne
	@JoinColumn(name = "app_user_id")
	private AppUserEntity user;

	private String description;

	@NotNull
	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;
}

