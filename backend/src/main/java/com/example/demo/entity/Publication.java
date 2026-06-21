package com.example.demo.entity;

import com.example.demo.enums.PublicationStatus;
import com.example.demo.enums.PublicationType;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "publications")
public class Publication extends BaseEntity {

    private String title;

    @Column(length = 5000)
    private String abstractText;

    private String keywords;

    @Enumerated(EnumType.STRING)
    private PublicationType publicationType;

    private String journalName;

    private String conferenceName;

    private LocalDate publicationDate;

    private String doi;

    @Enumerated(EnumType.STRING)
    private PublicationStatus status;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
    
    public Publication() {
    	
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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
}