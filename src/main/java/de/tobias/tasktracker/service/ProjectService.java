package de.tobias.tasktracker.service;

import de.tobias.tasktracker.converter.ProjectConverter;
import de.tobias.tasktracker.database.entity.AppUserEntity;
import de.tobias.tasktracker.database.entity.ProjectEntity;
import de.tobias.tasktracker.database.repository.ProjectRepository;
import de.tobias.tasktracker.dto.ProjectRequestDto;
import de.tobias.tasktracker.dto.ProjectResponseDto;
import de.tobias.tasktracker.exception.ProjectNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectService {

	private final ProjectRepository repository;
	private final ProjectConverter converter;

	private final UserService userService;

	public ProjectService(ProjectRepository repository, ProjectConverter converter, UserService userService) {
		this.repository = repository;
		this.converter = converter;
		this.userService = userService;
	}

	public List<ProjectResponseDto> getAllProjects() {
		final List<ProjectEntity> projects = repository.findAllProjectsWithUsers();
		final List<ProjectResponseDto> result = new ArrayList<>();

		projects.forEach(p -> result.add(converter.entityToDto(p)));
		return result;
	}

	public ProjectResponseDto createProject(ProjectRequestDto projectDto) {
		final AppUserEntity adminUser = userService.findAdminUser();

		final ProjectEntity newEntity = converter.dtoToEntity(projectDto, adminUser);
		final ProjectEntity savedEntity = repository.save(newEntity);
		return converter.entityToCreateResponseDto(savedEntity);
	}

	public ProjectResponseDto getProjectById(UUID id) {
		final Optional<ProjectEntity> result = repository.findById(id);
		if (result.isPresent()) {
			return converter.entityToDto(result.get());
		} else {
			throw new ProjectNotFoundException("Project with id " + id + " not found");
		}
	}

	public ProjectResponseDto updateProjectById(UUID id, ProjectRequestDto projectDto) {
		final Optional<ProjectEntity> result = repository.findById(id);
		if (result.isPresent()) {
			ProjectEntity oldEntity = result.get();
			oldEntity.setName(projectDto.getName());
			oldEntity.setDescription(projectDto.getDescription());
			oldEntity.setUpdatedAt(Instant.now());
			final ProjectEntity updatedEntity = repository.save(oldEntity);
			return converter.entityToCreateResponseDto(updatedEntity);
		} else  {
			throw new ProjectNotFoundException("Project with id " + id + " not found");
		}
	}
}
