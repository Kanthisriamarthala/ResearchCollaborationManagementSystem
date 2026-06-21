package com.example.demo.dto.response;

import com.example.demo.enums.TaskPriority;
import com.example.demo.enums.TaskStatus;

import java.time.LocalDate;

public class TaskResponseDTO {

    private Long id;

    private String title;

    private String description;

    private TaskPriority priority;

    private TaskStatus status;

    private LocalDate deadline;

    public TaskResponseDTO() {
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

	public TaskPriority getPriority() {
		return priority;
	}

	public void setPriority(TaskPriority priority) {
		this.priority = priority;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public LocalDate getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

    // Generate Getters and Setters
}