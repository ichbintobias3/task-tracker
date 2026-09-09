package de.tobias.tasktracker.api;

import de.tobias.tasktracker.dto.ProjectRequestDto;
import de.tobias.tasktracker.dto.ProjectResponseDto;
import de.tobias.tasktracker.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

	private final ProjectService service;

	public ProjectController(ProjectService service) {
		this.service = service;
	}

	@GetMapping
	public List<ProjectResponseDto> getProjects() {
		return service.getAllProjects();
	}

	@PostMapping
	public ProjectResponseDto createProject(@RequestBody @Valid ProjectRequestDto dto) {
		return service.createProject(dto);
	}

	@GetMapping("/{id}")
	public ProjectResponseDto getProjectById(@PathVariable UUID id) {
		return service.getProjectById(id);
	}

	@PutMapping("/{id}")
	public ProjectResponseDto updateProjectById(@PathVariable UUID id, @RequestBody @Valid ProjectRequestDto dto) {
		return service.updateProjectById(id, dto);
	}
}
