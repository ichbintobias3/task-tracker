package de.tobias.tasktracker.util;

import de.tobias.tasktracker.dto.ExceptionDto;
import de.tobias.tasktracker.exception.ProjectCreationException;
import de.tobias.tasktracker.exception.ProjectDeletionException;
import de.tobias.tasktracker.exception.ProjectNotFoundException;
import de.tobias.tasktracker.exception.ProjectUpdateException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ProjectCreationException.class)
	public ExceptionDto handleException(ProjectCreationException exception) {
		ExceptionDto dto = new ExceptionDto();
		dto.setTimestamp(Instant.now());
		dto.setStatus(HttpStatus.BAD_REQUEST.value());
		dto.setMessage(exception.getMessage());
		return dto;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ExceptionDto handleException(MethodArgumentNotValidException exception) {
		final List<FieldError> errors = exception.getBindingResult().getFieldErrors();

		ExceptionDto dto = new ExceptionDto();
		dto.setTimestamp(Instant.now());
		dto.setStatus(HttpStatus.BAD_REQUEST.value());
		dto.setMessage(errors.getFirst().getDefaultMessage());
		return dto;
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ProjectNotFoundException.class)
	public ExceptionDto handleException(ProjectNotFoundException exception) {
		ExceptionDto dto = new ExceptionDto();
		dto.setTimestamp(Instant.now());
		dto.setStatus(HttpStatus.NOT_FOUND.value());
		dto.setMessage(exception.getMessage());
		return dto;
	}

	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler({ ProjectDeletionException.class, ProjectUpdateException.class })
	public ExceptionDto handleException(RuntimeException exception) {
		ExceptionDto dto = new ExceptionDto();
		dto.setTimestamp(Instant.now());
		dto.setStatus(HttpStatus.CONFLICT.value());
		dto.setMessage(exception.getMessage());
		return dto;
	}
}
