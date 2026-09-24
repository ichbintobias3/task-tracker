package de.tobias.tasktracker.service;

import de.tobias.tasktracker.database.entity.AppUserEntity;
import de.tobias.tasktracker.database.entity.ProjectEntity;
import de.tobias.tasktracker.database.entity.TaskEntity;
import de.tobias.tasktracker.database.repository.AppUserRepository;
import de.tobias.tasktracker.database.repository.ProjectRepository;
import de.tobias.tasktracker.database.repository.TaskRepository;
import de.tobias.tasktracker.dto.TaskRequestDto;
import de.tobias.tasktracker.dto.TaskResponseDto;
import de.tobias.tasktracker.dto.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskServiceItTest {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private AppUserRepository userRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private TaskService taskService;

	final UUID sampleProjectId = UUID.randomUUID();
	final UUID sampleUserId = UUID.randomUUID();

	final ProjectEntity sampleProject = new ProjectEntity();
	final AppUserEntity sampleUser = new AppUserEntity();
	final TaskRequestDto sampleTask = new TaskRequestDto();

	public TaskServiceItTest() {
		sampleUser.setId(sampleUserId);
		sampleUser.setName("Test " + UUID.randomUUID()); // randomly generated since the name must be unique

		sampleProject.setId(sampleProjectId);
		sampleProject.setUser(sampleUser);
		sampleProject.setName("Test " + UUID.randomUUID()); // randomly generated since the name must be unique
		sampleProject.setDescription("Created by integration tests");

		sampleTask.setProjectId(sampleProjectId);
		sampleTask.setName("Test Task");
		sampleTask.setUserId(sampleUserId);
		sampleTask.setDescription("Created by integration tests");
		sampleTask.setStatus(TaskStatus.BACKLOG);
	}

	@Test
	public void testCreateTaskSuccess() {
		userRepository.save(sampleUser);
		projectRepository.save(sampleProject);
		TaskResponseDto createdTask = taskService.createTask(sampleTask);

		TaskEntity foundTask = taskRepository.findById(createdTask.getId())
				.orElseThrow();

		assertNotNull(foundTask.getId());
		assertEquals(sampleTask.getName(), foundTask.getName());
		assertEquals(sampleTask.getProjectId(), foundTask.getProject().getId());
		assertEquals(sampleTask.getUserId(), foundTask.getUser().getId());
		assertEquals(sampleTask.getDescription(), foundTask.getDescription());
		assertEquals(sampleTask.getStatus(), foundTask.getStatus());
		assertNotNull(foundTask.getCreatedAt());
		assertNull(foundTask.getUpdatedAt());
	}
}
