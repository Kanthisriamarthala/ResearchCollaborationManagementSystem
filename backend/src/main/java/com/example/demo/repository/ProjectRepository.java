package com.example.demo.repository;

import com.example.demo.entity.Project;
import com.example.demo.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByStatus(ProjectStatus status);

    List<Project> findByResearchDomain(String researchDomain);
    
    List<Project> findByTitleContainingIgnoreCase(String keyword);

}