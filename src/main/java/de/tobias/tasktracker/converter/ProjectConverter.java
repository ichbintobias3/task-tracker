package de.tobias.tasktracker.converter;

import de.tobias.tasktracker.database.entity.AppUserEntity;
import de.tobias.tasktracker.database.entity.ProjectEntity;
import de.tobias.tasktracker.dto.CreateProjectRequestDto;
import de.tobias.tasktracker.dto.CreateProjectResponseDto;
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

	public ProjectEntity dtoToEntity(CreateProjectRequestDto dto, AppUserEntity owner) {
		ProjectEntity entity = new ProjectEntity();
		entity.setUser(owner);
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		return entity;
	}

	public CreateProjectResponseDto entityToCreateResponseDto(ProjectEntity entity) {
		CreateProjectResponseDto dto = new CreateProjectResponseDto();
		dto.setOwner(entity.getUser().getName());
		dto.setName(entity.getName());
		dto.setDescription(entity.getDescription());
		dto.setCreatedAt(entity.getCreatedAt());
		dto.setUpdatedAt(entity.getUpdatedAt());
		return dto;
	}
}
