package de.tobias.tasktracker.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class ExceptionDto {

	private Instant timestamp;
	private int status;
	private String message;
}
