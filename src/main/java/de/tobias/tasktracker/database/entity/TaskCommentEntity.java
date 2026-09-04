package de.tobias.tasktracker.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name = "task_comment")
public class TaskCommentEntity {

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "app_user_id")
	private AppUserEntity appUser;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "task_id")
	private TaskEntity task;

	@NotNull
	private String title;

	private String content;

	@NotNull
	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;
}
