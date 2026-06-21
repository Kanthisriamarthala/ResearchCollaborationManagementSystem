package com.example.demo.dto.response;

import com.example.demo.enums.ProjectStatus;

import java.time.LocalDate;

public class ProjectResponseDTO {

    private Long id;

    private String title;

    private String description;

    private String researchDomain;

    private LocalDate startDate;

    private LocalDate endDate;

    private ProjectStatus status;

    public ProjectResponseDTO() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getResearchDomain() {
		return researchDomain;
	}

	public void setResearchDomain(String researchDomain) {
		this.researchDomain = researchDomain;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public ProjectStatus getStatus() {
		return status;
	}

	public void setStatus(ProjectStatus status) {
		this.status = status;
	}

    // Generate Getters and Setters
}