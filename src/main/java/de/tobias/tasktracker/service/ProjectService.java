package de.tobias.tasktracker.service;

import de.tobias.tasktracker.converter.ProjectConverter;
import de.tobias.tasktracker.database.entity.AppUserEntity;
import de.tobias.tasktracker.database.entity.ProjectEntity;
import de.tobias.tasktracker.database.repository.ProjectRepository;
import de.tobias.tasktracker.dto.CreateProjectRequestDto;
import de.tobias.tasktracker.dto.CreateProjectResponseDto;
import de.tobias.tasktracker.dto.ProjectDto;
import de.tobias.tasktracker.exception.ProjectNotFoundException;
import org.springframework.stereotype.Service;

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

	public List<ProjectDto> getAllProjects() {
		final List<ProjectEntity> projects = repository.findAllProjectsWithUsers();
		final List<ProjectDto> result = new ArrayList<>();

		projects.forEach(p -> result.add(converter.entityToDto(p)));
		return result;
	}

	public CreateProjectResponseDto createProject(CreateProjectRequestDto projectDto) {
		final AppUserEntity adminUser = userService.findAdminUser();

		final ProjectEntity newEntity = converter.dtoToEntity(projectDto, adminUser);
		final ProjectEntity savedEntity = repository.save(newEntity);
		return converter.entityToCreateResponseDto(savedEntity);
	}

	public ProjectDto getProjectById(UUID id) {
		final Optional<ProjectEntity> result = repository.findById(id);
		if (result.isPresent()) {
			return converter.entityToDto(result.get());
		} else {
			throw new ProjectNotFoundException("Project with id " + id + " not found");
		}
	}
}
