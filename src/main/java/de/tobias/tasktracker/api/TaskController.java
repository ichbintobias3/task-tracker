package de.tobias.tasktracker.api;

import de.tobias.tasktracker.dto.TaskRequestDto;
import de.tobias.tasktracker.dto.TaskResponseDto;
import de.tobias.tasktracker.dto.TaskStatus;
import de.tobias.tasktracker.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

	@PutMapping("/{id}")
	public TaskResponseDto updateTaskById(@PathVariable UUID id, @RequestBody @Valid TaskRequestDto dto) {
		return service.updateTaskById(id, dto);
	}

	@GetMapping
	public List<TaskResponseDto> getTasksByFilter(@RequestParam(required = false) UUID projectId, @RequestParam(required = false) UUID userId,
												  @RequestParam(required = false) Boolean unassigned, @RequestParam(required = false) TaskStatus status) {
		return service.getTasksByFilter(projectId, userId, unassigned, status);
	}
}
