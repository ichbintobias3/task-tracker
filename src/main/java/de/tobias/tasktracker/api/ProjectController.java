package de.tobias.tasktracker.api;

import de.tobias.tasktracker.dto.CreateProjectRequestDto;
import de.tobias.tasktracker.dto.CreateProjectResponseDto;
import de.tobias.tasktracker.dto.ProjectDto;
import de.tobias.tasktracker.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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

	@PostMapping
	public CreateProjectResponseDto createProject(@RequestBody @Valid CreateProjectRequestDto dto) {
		return service.createProject(dto);
	}
}
