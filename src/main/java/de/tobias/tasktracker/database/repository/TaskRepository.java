package de.tobias.tasktracker.database.repository;

import de.tobias.tasktracker.database.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

	
}
