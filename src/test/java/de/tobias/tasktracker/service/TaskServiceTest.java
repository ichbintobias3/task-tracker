package de.tobias.tasktracker.service;

import de.tobias.tasktracker.converter.TaskConverter;
import de.tobias.tasktracker.database.entity.AppUserEntity;
import de.tobias.tasktracker.database.entity.ProjectEntity;
import de.tobias.tasktracker.database.entity.TaskEntity;
import de.tobias.tasktracker.database.repository.TaskRepository;
import de.tobias.tasktracker.dto.TaskRequestDto;
import de.tobias.tasktracker.dto.TaskResponseDto;
import de.tobias.tasktracker.dto.TaskStatus;
import de.tobias.tasktracker.exception.ProjectNotFoundException;
import de.tobias.tasktracker.exception.TaskNotFoundException;
import de.tobias.tasktracker.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

	final TaskRepository repository = mock(TaskRepository.class);
	final TaskConverter converter = new TaskConverter();
	final ProjectService projectService = mock(ProjectService.class);
	final UserService userService = mock(UserService.class);

	final TaskService service = new TaskService(repository, converter, projectService, userService);

	final UUID testProjectId = UUID.randomUUID();
	final UUID testUserId = UUID.randomUUID();
	final UUID testTaskId = UUID.randomUUID();

	final ProjectEntity sampleProject = new ProjectEntity();
	final AppUserEntity sampleUser = new AppUserEntity();
	final TaskEntity sampleTask = new TaskEntity();

	final Instant taskCreatedAt = Instant.now();

	public TaskServiceTest() {
		sampleProject.setId(testProjectId);
		sampleProject.setUser(null);
		sampleProject.setName("Project Name");
		sampleProject.setDescription("Project Description");
		sampleProject.setCreatedAt(Instant.now());

		sampleUser.setId(testUserId);
		sampleUser.setName("User Name");
		sampleUser.setCreatedAt(Instant.now());

		sampleTask.setId(testTaskId);
		sampleTask.setProject(sampleProject);
		sampleTask.setName("Test Task");
		sampleTask.setStatus(TaskStatus.BACKLOG);
		sampleTask.setUser(sampleUser);
		sampleTask.setDescription("Test Task");
		sampleTask.setCreatedAt(taskCreatedAt);
	}

	@Test
	void testCreateTaskSuccess() {
		final TaskRequestDto input = new TaskRequestDto();
		input.setName("Test Task");
		input.setProjectId(testProjectId);
		input.setUserId(testUserId);
		input.setDescription("Test Task");
		input.setStatus(TaskStatus.BACKLOG);

		final TaskResponseDto expected = new TaskResponseDto();
		expected.setId(testTaskId);
		expected.setProjectId(testProjectId);
		expected.setUserId(testUserId);
		expected.setName("Test Task");
		expected.setStatus(TaskStatus.BACKLOG);
		expected.setDescription("Test Task");
		expected.setCreatedAt(taskCreatedAt);
		expected.setUpdatedAt(null);

		when(projectService.getProjectEntityById(testProjectId))
				.thenReturn(sampleProject);
		when(userService.getUserEntityById(testUserId))
			.thenReturn(sampleUser);
		when(repository.save(any()))
				.thenReturn(sampleTask);

		final TaskResponseDto output = service.createTask(input);

		ArgumentCaptor<TaskEntity> captor = ArgumentCaptor.forClass(TaskEntity.class);
		verify(repository).save(captor.capture());
		TaskEntity savedTask = captor.getValue();

		assertNotNull(savedTask.getId()); // can't check if it's the same because it gets randomly generated
		assertEquals(sampleTask.getProject().getId(), savedTask.getProject().getId());
		assertEquals(sampleTask.getUser().getId(), savedTask.getUser().getId());
		assertEquals(sampleTask.getName(), savedTask.getName());
		assertEquals(sampleTask.getStatus(), savedTask.getStatus());
		assertEquals(sampleTask.getDescription(), savedTask.getDescription());

		assertNotNull(output.getId());
		assertEquals(expected, output);
	}

	@Test
	void testCreateTaskProjectNotFound() {
		final UUID projectId = UUID.randomUUID();
		final UUID userId = UUID.randomUUID();

		final TaskRequestDto input = new TaskRequestDto();
		input.setName("Test Task");
		input.setProjectId(projectId);
		input.setUserId(userId);
		input.setDescription("Test Task");
		input.setStatus(TaskStatus.BACKLOG);

		when(projectService.getProjectEntityById(projectId))
				.thenThrow(new ProjectNotFoundException("Project with id " + projectId + " not found"));

		assertThrows(ProjectNotFoundException.class, () -> service.createTask(input));
	}

	@Test
	void testCreateTaskUserNotFound() {
		final UUID projectId = UUID.randomUUID();
		final UUID userId = UUID.randomUUID();

		final TaskRequestDto input = new TaskRequestDto();
		input.setName("Test Task");
		input.setProjectId(projectId);
		input.setUserId(userId);
		input.setDescription("Test Task");
		input.setStatus(TaskStatus.BACKLOG);

		when(projectService.getProjectEntityById(projectId))
				.thenReturn(sampleProject);
		when(userService.getUserEntityById(userId))
				.thenThrow(UserNotFoundException.class);

		assertThrows(UserNotFoundException.class, () -> service.createTask(input));
	}

	@Test
	void testCreateTaskNoUserProvided() {
		final TaskRequestDto input = new TaskRequestDto();
		input.setName("Test Task");
		input.setProjectId(testProjectId);
		input.setUserId(null);
		input.setDescription("Test Task");
		input.setStatus(TaskStatus.BACKLOG);

		final TaskResponseDto expected = new TaskResponseDto();
		expected.setId(testTaskId);
		expected.setProjectId(testProjectId);
		expected.setUserId(null);
		expected.setName("Test Task");
		expected.setStatus(TaskStatus.BACKLOG);
		expected.setDescription("Test Task");
		expected.setCreatedAt(taskCreatedAt);
		expected.setUpdatedAt(null);

		final TaskEntity sampleTaskWithoutUser = new TaskEntity();
		sampleTaskWithoutUser.setId(testTaskId);
		sampleTaskWithoutUser.setProject(sampleProject);
		sampleTaskWithoutUser.setName("Test Task");
		sampleTaskWithoutUser.setStatus(TaskStatus.BACKLOG);
		sampleTaskWithoutUser.setUser(null);
		sampleTaskWithoutUser.setDescription("Test Task");
		sampleTaskWithoutUser.setCreatedAt(taskCreatedAt);

		when(projectService.getProjectEntityById(testProjectId))
				.thenReturn(sampleProject);
		when(repository.save(any()))
				.thenReturn(sampleTaskWithoutUser);

		final TaskResponseDto output = service.createTask(input);

		verify(userService, never())
				.getUserEntityById(any());

		ArgumentCaptor<TaskEntity> captor = ArgumentCaptor.forClass(TaskEntity.class);
		verify(repository).save(captor.capture());
		TaskEntity savedTask = captor.getValue();

		assertEquals(testProjectId, savedTask.getProject().getId());
		assertNull(savedTask.getUser());

		assertEquals(expected, output);
	}

	@Test
	void testGetTaskByIdSuccess() {
		final TaskResponseDto expected = new TaskResponseDto();
		expected.setId(testTaskId);
		expected.setProjectId(testProjectId);
		expected.setUserId(testUserId);
		expected.setName("Test Task");
		expected.setStatus(TaskStatus.BACKLOG);
		expected.setDescription("Test Task");
		expected.setCreatedAt(taskCreatedAt);
		expected.setUpdatedAt(null);

		when(repository.findById(any()))
				.thenReturn(Optional.of(sampleTask));

		final TaskResponseDto output = service.getTaskById(testTaskId);

		assertEquals(expected, output);
	}

	@Test
	void testGetTaskByIdTaskNotFound() {
		final UUID taskId = UUID.randomUUID();

		when(repository.findById(taskId))
				.thenReturn(Optional.empty());

		assertThrows(TaskNotFoundException.class, () -> service.getTaskById(taskId));
	}
}
