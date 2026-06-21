package com.example.demo.entity;

import com.example.demo.enums.FundingStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "funding")
public class Funding extends BaseEntity {

    private String fundingAgency;

    private Double amount;

    private LocalDate applicationDate;

    private LocalDate approvalDate;

    @Enumerated(EnumType.STRING)
    private FundingStatus status;

    @Column(length = 2000)
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    
    public Funding() {
    	
    }

	public String getFundingAgency() {
		return fundingAgency;
	}

	public void setFundingAgency(String fundingAgency) {
		this.fundingAgency = fundingAgency;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LocalDate getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(LocalDate applicationDate) {
		this.applicationDate = applicationDate;
	}

	public LocalDate getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(LocalDate approvalDate) {
		this.approvalDate = approvalDate;
	}

	public FundingStatus getStatus() {
		return status;
	}

	public void setStatus(FundingStatus status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
    
    
}