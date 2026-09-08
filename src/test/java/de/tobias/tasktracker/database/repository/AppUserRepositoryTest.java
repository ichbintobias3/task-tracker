package de.tobias.tasktracker.database.repository;

import de.tobias.tasktracker.database.entity.AppUserEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AppUserRepositoryTest {

	@Autowired
	private AppUserRepository repository;

	@Test
	@Transactional
	public void getAppUserFromDb() {
		final AppUserEntity appUser = repository.findAll().getFirst();
		System.out.println(appUser);
	}
}
