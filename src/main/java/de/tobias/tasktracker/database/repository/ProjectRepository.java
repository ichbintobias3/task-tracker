package de.tobias.tasktracker.database.repository;

import de.tobias.tasktracker.database.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, UUID> {

	@Query(value = "SELECT p FROM ProjectEntity p JOIN FETCH p.user")
	List<ProjectEntity> findAllProjectsWithUsers();
}
