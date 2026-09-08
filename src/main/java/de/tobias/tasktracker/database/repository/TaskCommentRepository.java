package de.tobias.tasktracker.database.repository;

import de.tobias.tasktracker.database.entity.TaskCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskCommentRepository extends JpaRepository<TaskCommentEntity, UUID> {

	@Query(value = "SELECT tc FROM TaskCommentEntity tc JOIN FETCH tc.task")
	List<TaskCommentEntity> findAllWithTasks();
}
