package de.tobias.tasktracker.util;

import de.tobias.tasktracker.dto.ExceptionDto;
import de.tobias.tasktracker.exception.ProjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
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
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ExceptionDto handleException(DataIntegrityViolationException exception) {
		ExceptionDto dto = new ExceptionDto();
		dto.setTimestamp(Instant.now());
		dto.setStatus(HttpStatus.BAD_REQUEST.value());
		dto.setMessage("The project name already exists.");
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
}
