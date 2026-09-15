package de.tobias.tasktracker.service;

import de.tobias.tasktracker.database.entity.AppUserEntity;
import de.tobias.tasktracker.database.repository.AppUserRepository;
import de.tobias.tasktracker.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

	private final AppUserRepository repository;

	public UserService(final AppUserRepository repository) {
		this.repository = repository;
	}

	public AppUserEntity findAdminUser() {
		return repository.findAll().getFirst();
	}

	public AppUserEntity getUserEntityById(UUID id) {
		return repository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
	}
}
