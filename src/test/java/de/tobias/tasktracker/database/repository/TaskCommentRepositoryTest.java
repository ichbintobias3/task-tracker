package de.tobias.tasktracker.database.repository;

import de.tobias.tasktracker.database.entity.AppUserEntity;
import de.tobias.tasktracker.database.entity.ProjectEntity;
import de.tobias.tasktracker.database.entity.TaskCommentEntity;
import de.tobias.tasktracker.database.entity.TaskEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TaskCommentRepositoryTest {

	@Autowired
	private TaskCommentRepository repository;

	@Test
	public void getTaskCommentFromDb() {
		final TaskCommentEntity taskComment = repository.findAll().getFirst();
		final TaskEntity task = taskComment.getTask();
		final ProjectEntity project = task.getProject();
		final AppUserEntity appUser = project.getUser();
		System.out.println(taskComment);
		System.out.println(task);
		System.out.println(project);
		System.out.println(appUser);
	}
}
