package de.tobias.tasktracker.service;

import de.tobias.tasktracker.converter.ProjectConverter;
import de.tobias.tasktracker.database.entity.ProjectEntity;
import de.tobias.tasktracker.database.repository.ProjectRepository;
import de.tobias.tasktracker.dto.ProjectDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

	private final ProjectRepository repository;
	private final ProjectConverter converter;

	public ProjectService(ProjectRepository repository, ProjectConverter converter) {
		this.repository = repository;
		this.converter = converter;
	}

	public List<ProjectDto> getAllProjects() {
		final List<ProjectEntity> projects = repository.findAllProjectsWithUsers();
		final List<ProjectDto> result = new ArrayList<>();

		projects.forEach(p -> result.add(converter.entityToDto(p)));
		return result;
	}
}
