package de.tobias.tasktracker.service;

import de.tobias.tasktracker.converter.TaskConverter;
import de.tobias.tasktracker.database.entity.AppUserEntity;
import de.tobias.tasktracker.database.entity.ProjectEntity;
import de.tobias.tasktracker.database.entity.TaskEntity;
import de.tobias.tasktracker.database.repository.TaskRepository;
import de.tobias.tasktracker.database.specification.TaskSpecification;
import de.tobias.tasktracker.dto.TaskRequestDto;
import de.tobias.tasktracker.dto.TaskResponseDto;
import de.tobias.tasktracker.dto.TaskStatus;
import de.tobias.tasktracker.exception.TaskNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

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

	public TaskResponseDto updateTaskById(UUID id, TaskRequestDto taskDto) {
		final Optional<TaskEntity> result = repository.findById(id);
		if (result.isPresent()) {
			final TaskEntity oldEntity = result.get();

			if (!oldEntity.getProject().getId().equals(taskDto.getProjectId())) {
				final ProjectEntity project = projectService.getProjectEntityById(taskDto.getProjectId());
				oldEntity.setProject(project);
			}
			final UUID oldUserId = oldEntity.getUser() == null ? null : oldEntity.getUser().getId();
			final UUID newUserId = taskDto.getUserId();
			if (!Objects.equals(oldUserId, newUserId)) {
				if (newUserId != null) {
					final AppUserEntity user = userService.getUserEntityById(taskDto.getUserId());
					oldEntity.setUser(user);
				} else {
					oldEntity.setUser(null);
				}
			}

			oldEntity.setName(taskDto.getName());
			oldEntity.setDescription(taskDto.getDescription());
			oldEntity.setStatus(taskDto.getStatus());
			oldEntity.setUpdatedAt(Instant.now());
			final TaskEntity updatedEntity = repository.save(oldEntity);
			return converter.entityToResponseDto(updatedEntity);
		} else {
			throw new TaskNotFoundException("Task with id " + id + " not found");
		}
	}

	public List<TaskResponseDto> getTasksByFilter(UUID projectId, UUID userId, Boolean isUnassigned, TaskStatus status) {
		final List<TaskEntity> results = repository.findAll(TaskSpecification.allFilters(projectId, userId, isUnassigned, status));

		final List<TaskResponseDto> dtos = new ArrayList<>();
		for (final TaskEntity taskEntity : results) {
			dtos.add(converter.entityToResponseDto(taskEntity));
		}
		return dtos;
	}
}
