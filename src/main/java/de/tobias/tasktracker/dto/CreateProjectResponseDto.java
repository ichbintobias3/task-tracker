package de.tobias.tasktracker.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class CreateProjectResponseDto {

	private String owner;
	private String name;
	private String description;
	private Instant createdAt;
	private Instant updatedAt;
}
