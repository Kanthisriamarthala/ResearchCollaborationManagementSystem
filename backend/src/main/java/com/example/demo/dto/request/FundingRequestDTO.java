package com.example.demo.dto.request;

import com.example.demo.enums.FundingStatus;

import java.time.LocalDate;

public class FundingRequestDTO {

    private String fundingAgency;
    private Double amount;
    private LocalDate applicationDate;
    private LocalDate approvalDate;
    private FundingStatus status;
    private String remarks;
    private Long projectId;

    public FundingRequestDTO() {
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

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

    // Generate Getters and Setters
}