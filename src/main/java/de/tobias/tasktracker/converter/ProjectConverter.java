package de.tobias.tasktracker.converter;

import de.tobias.tasktracker.database.entity.AppUserEntity;
import de.tobias.tasktracker.database.entity.ProjectEntity;
import de.tobias.tasktracker.dto.ProjectRequestDto;
import de.tobias.tasktracker.dto.ProjectResponseDto;
import org.springframework.stereotype.Service;

@Service
public class ProjectConverter {

	public ProjectResponseDto entityToDto(ProjectEntity entity) {
		ProjectResponseDto dto = new ProjectResponseDto();
		dto.setOwner(entity.getUser().getName());
		dto.setName(entity.getName());
		dto.setDescription(entity.getDescription());
		dto.setCreatedAt(entity.getCreatedAt());
		dto.setUpdatedAt(entity.getUpdatedAt());
		return dto;
	}

	public ProjectEntity dtoToEntity(ProjectRequestDto dto, AppUserEntity owner) {
		ProjectEntity entity = new ProjectEntity();
		entity.setUser(owner);
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		return entity;
	}

	public ProjectResponseDto entityToCreateResponseDto(ProjectEntity entity) {
		ProjectResponseDto dto = new ProjectResponseDto();
		dto.setOwner(entity.getUser().getName());
		dto.setName(entity.getName());
		dto.setDescription(entity.getDescription());
		dto.setCreatedAt(entity.getCreatedAt());
		dto.setUpdatedAt(entity.getUpdatedAt());
		return dto;
	}
}
