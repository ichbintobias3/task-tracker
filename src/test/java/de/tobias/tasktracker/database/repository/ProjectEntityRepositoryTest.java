package de.tobias.tasktracker.database.repository;

import de.tobias.tasktracker.database.entity.AppUserEntity;
import de.tobias.tasktracker.database.entity.ProjectEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProjectEntityRepositoryTest {

	@Autowired
	private ProjectRepository repository;

	@Test
	@Transactional
	public void getProjectFromDb() {
		final ProjectEntity project =  repository.findAll().getFirst();
		final AppUserEntity appUser = project.getUser();
		System.out.println(project);
		System.out.println(appUser);
	}
}
