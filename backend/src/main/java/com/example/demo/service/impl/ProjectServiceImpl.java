package com.example.demo.service.impl;

import com.example.demo.dto.request.ProjectRequestDTO;
import com.example.demo.dto.response.ProjectResponseDTO;
import com.example.demo.entity.Project;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectResponseDTO createProject(ProjectRequestDTO request) {

        Project project = new Project();

        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setResearchDomain(request.getResearchDomain());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        project.setStatus(request.getStatus());

        return mapToResponse(projectRepository.save(project));
    }

    @Override
    public ProjectResponseDTO getProjectById(Long id) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found with id: " + id));

        return mapToResponse(project);
    }

    @Override
    public List<ProjectResponseDTO> getAllProjects() {

        return projectRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectResponseDTO updateProject(Long id, ProjectRequestDTO request) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found with id: " + id));

        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setResearchDomain(request.getResearchDomain());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        project.setStatus(request.getStatus());

        return mapToResponse(projectRepository.save(project));
    }

    @Override
    public void deleteProject(Long id) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found with id: " + id));

        projectRepository.delete(project);
    }

    private ProjectResponseDTO mapToResponse(Project project) {

        ProjectResponseDTO dto = new ProjectResponseDTO();

        dto.setId(project.getId());
        dto.setTitle(project.getTitle());
        dto.setDescription(project.getDescription());
        dto.setResearchDomain(project.getResearchDomain());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setStatus(project.getStatus());

        return dto;
    }
    
    @Override
    public List<ProjectResponseDTO> searchProjects(
            String keyword) {

        return projectRepository
                .findByTitleContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}