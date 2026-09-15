package de.tobias.tasktracker.api;

import de.tobias.tasktracker.dto.TaskRequestDto;
import de.tobias.tasktracker.dto.TaskResponseDto;
import de.tobias.tasktracker.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

	private final TaskService service;

	public TaskController(TaskService service) {
		this.service = service;
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public TaskResponseDto createTask(@RequestBody @Valid TaskRequestDto dto) {
		return service.createTask(dto);
	}

	@GetMapping("/{id}")
	public TaskResponseDto getTaskById(@PathVariable UUID id) {
		return service.getTaskById(id);
	}
}
