package de.tobias.tasktracker.database.repository;

import de.tobias.tasktracker.database.entity.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUserEntity, Long> {

	
}
