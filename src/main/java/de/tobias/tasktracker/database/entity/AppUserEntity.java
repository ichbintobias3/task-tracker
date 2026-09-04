package de.tobias.tasktracker.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name = "app_user")
public class AppUserEntity {

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@NotNull
	private String name;

	@NotNull
	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;
}
