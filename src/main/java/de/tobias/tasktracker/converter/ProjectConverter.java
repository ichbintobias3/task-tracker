package de.tobias.tasktracker.converter;

import de.tobias.tasktracker.database.entity.ProjectEntity;
import de.tobias.tasktracker.dto.ProjectDto;
import org.springframework.stereotype.Service;

@Service
public class ProjectConverter {

	public ProjectDto entityToDto(ProjectEntity entity) {
		ProjectDto dto = new ProjectDto();
		dto.setOwner(entity.getUser().getName());
		dto.setName(entity.getName());
		dto.setDescription(entity.getDescription());
		dto.setCreatedAt(entity.getCreatedAt());
		dto.setUpdatedAt(entity.getUpdatedAt());
		return dto;
	}
}
