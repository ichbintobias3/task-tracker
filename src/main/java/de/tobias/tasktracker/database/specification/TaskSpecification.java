package de.tobias.tasktracker.database.specification;

import de.tobias.tasktracker.database.entity.TaskEntity;
import de.tobias.tasktracker.dto.TaskStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class TaskSpecification {

	public static Specification<TaskEntity> hasProjectId(final UUID projectId) {
		if (projectId == null) {
			return Specification.unrestricted();
		}
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("project").get("id"), projectId);
	}

	public static Specification<TaskEntity> hasUserId(final UUID userId) {
		if (userId == null) {
			return Specification.unrestricted();
		}
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId);
	}

	public static Specification<TaskEntity> isUnassigned(final Boolean unassigned) {
		if (unassigned == null) {
			return Specification.unrestricted();
		}
		return (root, query, cb) ->
				unassigned ? cb.isNull(root.get("user")) : cb.isNotNull(root.get("user"));
	}

	public static Specification<TaskEntity> hasStatus(final TaskStatus status) {
		if (status == null) {
			return Specification.unrestricted();
		}
		return (root, query, specificationBuilder) ->  specificationBuilder.equal(root.get("status"), status);
	}

	public static Specification<TaskEntity> allFilters(final UUID projectId, final UUID userId, final Boolean isUnassigned, final TaskStatus status) {
		return Specification.allOf(hasProjectId(projectId), hasUserId(userId), isUnassigned(isUnassigned), hasStatus(status));
	}
}
