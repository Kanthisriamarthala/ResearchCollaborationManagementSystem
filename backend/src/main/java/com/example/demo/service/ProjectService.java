package com.example.demo.service;

import com.example.demo.dto.request.ProjectRequestDTO;
import com.example.demo.dto.response.ProjectResponseDTO;

import java.util.List;

public interface ProjectService {

    ProjectResponseDTO createProject(ProjectRequestDTO request);

    ProjectResponseDTO getProjectById(Long id);

    List<ProjectResponseDTO> getAllProjects();

    ProjectResponseDTO updateProject(Long id, ProjectRequestDTO request);

    void deleteProject(Long id);
    
    List<ProjectResponseDTO> searchProjects(String keyword);
}