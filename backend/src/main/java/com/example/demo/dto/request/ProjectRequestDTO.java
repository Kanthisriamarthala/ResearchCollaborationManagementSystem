package com.example.demo.dto.request;

import com.example.demo.enums.ProjectStatus;

import java.time.LocalDate;

public class ProjectRequestDTO {

    private String title;

    private String description;

    private String researchDomain;

    private LocalDate startDate;

    private LocalDate endDate;

    private ProjectStatus status;

    public ProjectRequestDTO() {
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