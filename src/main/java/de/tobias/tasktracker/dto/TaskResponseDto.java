package de.tobias.tasktracker.dto;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class TaskResponseDto {

	private UUID id;
	private UUID projectId;
	private UUID userId;
	private String name;
	private String status;
	private String description;
	private Instant createdAt;
	private Instant updatedAt;
}
