package com.example.demo.controller;

import com.example.demo.common.ApiResponse;
import com.example.demo.dto.request.ProjectRequestDTO;
import com.example.demo.dto.response.ProjectResponseDTO;
import com.example.demo.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin("*")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ApiResponse<ProjectResponseDTO> createProject(
            @RequestBody ProjectRequestDTO request) {

        return new ApiResponse<>(
                true,
                "Project created successfully",
                projectService.createProject(request)
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<ProjectResponseDTO> getProjectById(
            @PathVariable Long id) {

        return new ApiResponse<>(
                true,
                "Project fetched successfully",
                projectService.getProjectById(id)
        );
    }

    @GetMapping
    public ApiResponse<List<ProjectResponseDTO>> getAllProjects() {

        return new ApiResponse<>(
                true,
                "Projects fetched successfully",
                projectService.getAllProjects()
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<ProjectResponseDTO> updateProject(
            @PathVariable Long id,
            @RequestBody ProjectRequestDTO request) {

        return new ApiResponse<>(
                true,
                "Project updated successfully",
                projectService.updateProject(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteProject(
            @PathVariable Long id) {

        projectService.deleteProject(id);

        return new ApiResponse<>(
                true,
                "Project deleted successfully",
                null
        );
    }
    
    @GetMapping("/search")
    public ApiResponse<List<ProjectResponseDTO>>
    searchProjects(
            @RequestParam String keyword) {

        return new ApiResponse<>(
                true,
                "Projects fetched successfully",
                projectService.searchProjects(keyword)
        );
    }
}