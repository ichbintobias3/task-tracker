package de.tobias.tasktracker.api;

import de.tobias.tasktracker.dto.ProjectDto;
import de.tobias.tasktracker.service.ProjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

	private final ProjectService service;

	public ProjectController(ProjectService service) {
		this.service = service;
	}

	@GetMapping
	public List<ProjectDto> getProjects() {
		return service.getAllProjects();
	}
}
