package de.tobias.tasktracker.database.repository;

import de.tobias.tasktracker.database.entity.AppUserEntity;
import de.tobias.tasktracker.database.entity.ProjectEntity;
import de.tobias.tasktracker.database.entity.TaskEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TaskRepositoryTest {

	@Autowired
	private TaskRepository repository;

	@Test
	@Transactional
	public void getTaskFromDB() {
		final TaskEntity task = repository.findAll().getFirst();
		final ProjectEntity project = task.getProject();
		final AppUserEntity appUser = project.getUser();
		System.out.println(task);
		System.out.println(project);
		System.out.println(appUser);
	}
}
