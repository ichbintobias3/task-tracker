package de.tobias.tasktracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class TaskRequestDto {

	@NotBlank
	private String name;

	@NotNull
	private UUID projectId;

	private UUID userId;
	private String description;
}
