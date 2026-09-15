package de.tobias.tasktracker.converter;

import de.tobias.tasktracker.database.entity.AppUserEntity;
import de.tobias.tasktracker.database.entity.ProjectEntity;
import de.tobias.tasktracker.database.entity.TaskEntity;
import de.tobias.tasktracker.dto.TaskRequestDto;
import de.tobias.tasktracker.dto.TaskResponseDto;
import org.springframework.stereotype.Service;

@Service
public class TaskConverter {

	public TaskResponseDto entityToResponseDto(TaskEntity entity) {
		TaskResponseDto responseDto = new TaskResponseDto();
		responseDto.setId(entity.getId());
		responseDto.setProjectId(entity.getProject().getId());
		if (entity.getUser() != null) {
			responseDto.setUserId(entity.getUser().getId());
		}
		responseDto.setName(entity.getName());
		responseDto.setStatus(entity.getStatus());
		responseDto.setDescription(entity.getDescription());
		responseDto.setCreatedAt(entity.getCreatedAt());
		responseDto.setUpdatedAt(entity.getUpdatedAt());
		return responseDto;
	}

	public TaskEntity requestDtoToEntity(TaskRequestDto dto, ProjectEntity project, AppUserEntity user) {
		TaskEntity taskEntity = new TaskEntity();
		taskEntity.setProject(project);
		taskEntity.setName(dto.getName());
		taskEntity.setStatus(dto.getStatus());
		taskEntity.setUser(user);
		taskEntity.setDescription(dto.getDescription());
		return taskEntity;
	}
}
