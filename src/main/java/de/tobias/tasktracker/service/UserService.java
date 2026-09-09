package de.tobias.tasktracker.service;

import de.tobias.tasktracker.database.entity.AppUserEntity;
import de.tobias.tasktracker.database.repository.AppUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private final AppUserRepository repository;

	public UserService(final AppUserRepository repository) {
		this.repository = repository;
	}

	public AppUserEntity findAdminUser() {
		return repository.findAll().getFirst();
	}
}
