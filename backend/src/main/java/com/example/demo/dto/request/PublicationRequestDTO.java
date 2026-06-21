package com.example.demo.dto.request;

import com.example.demo.enums.PublicationStatus;
import com.example.demo.enums.PublicationType;

import java.time.LocalDate;

public class PublicationRequestDTO {

    private String title;
    private String abstractText;
    private String keywords;
    private PublicationType publicationType;
    private String journalName;
    private String conferenceName;
    private LocalDate publicationDate;
    private String doi;
    private PublicationStatus status;
    private Long projectId;
    private Long createdById;

    public PublicationRequestDTO() {
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAbstractText() {
		return abstractText;
	}

	public void setAbstractText(String abstractText) {
		this.abstractText = abstractText;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public PublicationType getPublicationType() {
		return publicationType;
	}

	public void setPublicationType(PublicationType publicationType) {
		this.publicationType = publicationType;
	}

	public String getJournalName() {
		return journalName;
	}

	public void setJournalName(String journalName) {
		this.journalName = journalName;
	}

	public String getConferenceName() {
		return conferenceName;
	}

	public void setConferenceName(String conferenceName) {
		this.conferenceName = conferenceName;
	}

	public LocalDate getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(LocalDate publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getDoi() {
		return doi;
	}

	public void setDoi(String doi) {
		this.doi = doi;
	}

	public PublicationStatus getStatus() {
		return status;
	}

	public void setStatus(PublicationStatus status) {
		this.status = status;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

    // Generate Getters and Setters
}