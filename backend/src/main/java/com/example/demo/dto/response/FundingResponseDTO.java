package com.example.demo.dto.response;

import com.example.demo.enums.FundingStatus;

import java.time.LocalDate;

public class FundingResponseDTO {

    private Long id;
    private String fundingAgency;
    private Double amount;
    private LocalDate applicationDate;
    private LocalDate approvalDate;
    private FundingStatus status;
    private String remarks;

    public FundingResponseDTO() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

    // Generate Getters and Setters
}