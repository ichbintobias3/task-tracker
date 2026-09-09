package de.tobias.tasktracker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateProjectRequestDto {

	@NotBlank(message = "Project name is mandatory.")
	private String name;

	private String description;
}
