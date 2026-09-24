package de.tobias.tasktracker.database.repository;

import de.tobias.tasktracker.database.entity.AppUserEntity;
import de.tobias.tasktracker.database.entity.ProjectEntity;
import de.tobias.tasktracker.database.entity.TaskEntity;
import de.tobias.tasktracker.dto.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskRepositoryItTest {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private AppUserRepository userRepository;

	@Autowired
	private TaskRepository taskRepository;

	final ProjectEntity sampleProject = new ProjectEntity();
	final AppUserEntity sampleUser = new AppUserEntity();
	final TaskEntity sampleTask = new TaskEntity();

	public TaskRepositoryItTest() {
		sampleUser.setName("Test " + UUID.randomUUID()); // randomly generated since the name must be unique

		sampleProject.setUser(sampleUser);
		sampleProject.setName("Test " + UUID.randomUUID()); // randomly generated since the name must be unique
		sampleProject.setDescription("Created by integration tests");

		sampleTask.setProject(sampleProject);
		sampleTask.setName("Test Task");
		sampleTask.setStatus(TaskStatus.BACKLOG);
		sampleTask.setUser(sampleUser);
		sampleTask.setDescription("Created by integration tests");
	}

	@Test
	public void testCreateTaskSuccess() {
		userRepository.save(sampleUser);
		projectRepository.save(sampleProject);
		TaskEntity createdTask = taskRepository.save(sampleTask);

		TaskEntity foundTask = taskRepository.findById(createdTask.getId())
						.orElseThrow();

		assertNotNull(foundTask.getId());
		assertEquals(sampleTask.getName(), foundTask.getName());
		assertEquals(sampleTask.getStatus(), foundTask.getStatus());
		assertEquals(sampleTask.getProject().getId(), foundTask.getProject().getId());
		assertEquals(sampleTask.getUser().getId(), foundTask.getUser().getId());
		assertEquals(sampleTask.getDescription(), foundTask.getDescription());
		assertNotNull(foundTask.getCreatedAt());
		assertNull(foundTask.getUpdatedAt());
	}
}
