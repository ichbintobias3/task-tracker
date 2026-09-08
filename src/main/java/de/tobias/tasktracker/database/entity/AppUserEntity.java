package de.tobias.tasktracker.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "app_user")
public class AppUserEntity {

	@Id
	private UUID id;

	@NotNull
	private String name;

	@NotNull
	@Column(name = "created_at")
	private Instant createdAt;

	@Column(name = "updated_at")
	private Instant updatedAt;

	public AppUserEntity() {
		this.id = UUID.randomUUID();
	}
}
