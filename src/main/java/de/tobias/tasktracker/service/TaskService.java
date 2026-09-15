package de.tobias.tasktracker.service;

import de.tobias.tasktracker.converter.TaskConverter;
import de.tobias.tasktracker.database.entity.AppUserEntity;
import de.tobias.tasktracker.database.entity.ProjectEntity;
import de.tobias.tasktracker.database.entity.TaskEntity;
import de.tobias.tasktracker.database.repository.TaskRepository;
import de.tobias.tasktracker.dto.TaskRequestDto;
import de.tobias.tasktracker.dto.TaskResponseDto;
import de.tobias.tasktracker.exception.TaskNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

	private final TaskRepository repository;
	private final TaskConverter converter;

	private final ProjectService projectService;
	private final UserService userService;

	public TaskService(TaskRepository repository, TaskConverter converter, ProjectService projectService, UserService userService) {
		this.repository = repository;
		this.converter = converter;
		this.projectService = projectService;
		this.userService = userService;
	}

	public TaskResponseDto createTask(TaskRequestDto taskDto) {
		final ProjectEntity project = projectService.getProjectEntityById(taskDto.getProjectId());

		final UUID userId = taskDto.getUserId();
		final AppUserEntity user = userId != null ? userService.getUserEntityById(taskDto.getUserId()) : null;

		final TaskEntity newEntity = converter.requestDtoToEntity(taskDto, project, user);
		final TaskEntity savedEntity = repository.save(newEntity);
		return converter.entityToResponseDto(savedEntity);
	}

	public TaskResponseDto getTaskById(UUID id) {
		final Optional<TaskEntity> result = repository.findById(id);
		if (result.isPresent()) {
			return converter.entityToResponseDto(result.get());
		} else {
			throw new TaskNotFoundException("Task with id " + id + " not found");
		}
	}
}
