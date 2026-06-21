package com.example.demo.service.impl;

import com.example.demo.dto.request.FundingRequestDTO;
import com.example.demo.dto.response.FundingResponseDTO;
import com.example.demo.entity.Funding;
import com.example.demo.entity.Project;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.FundingRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.service.FundingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FundingServiceImpl implements FundingService {

    private final FundingRepository fundingRepository;
    private final ProjectRepository projectRepository;

    public FundingServiceImpl(
            FundingRepository fundingRepository,
            ProjectRepository projectRepository) {
        this.fundingRepository = fundingRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public FundingResponseDTO createFunding(FundingRequestDTO request) {

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found"));

        Funding funding = new Funding();

        funding.setFundingAgency(request.getFundingAgency());
        funding.setAmount(request.getAmount());
        funding.setApplicationDate(request.getApplicationDate());
        funding.setApprovalDate(request.getApprovalDate());
        funding.setStatus(request.getStatus());
        funding.setRemarks(request.getRemarks());
        funding.setProject(project);

        return mapToResponse(fundingRepository.save(funding));
    }

    @Override
    public FundingResponseDTO getFundingById(Long id) {

        Funding funding = fundingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Funding not found"));

        return mapToResponse(funding);
    }

    @Override
    public List<FundingResponseDTO> getAllFunding() {

        return fundingRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FundingResponseDTO updateFunding(Long id,
                                            FundingRequestDTO request) {

        Funding funding = fundingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Funding not found"));

        funding.setFundingAgency(request.getFundingAgency());
        funding.setAmount(request.getAmount());
        funding.setApplicationDate(request.getApplicationDate());
        funding.setApprovalDate(request.getApprovalDate());
        funding.setStatus(request.getStatus());
        funding.setRemarks(request.getRemarks());

        return mapToResponse(fundingRepository.save(funding));
    }

    @Override
    public void deleteFunding(Long id) {

        Funding funding = fundingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Funding not found"));

        fundingRepository.delete(funding);
    }

    private FundingResponseDTO mapToResponse(Funding funding) {

        FundingResponseDTO dto = new FundingResponseDTO();

        dto.setId(funding.getId());
        dto.setFundingAgency(funding.getFundingAgency());
        dto.setAmount(funding.getAmount());
        dto.setApplicationDate(funding.getApplicationDate());
        dto.setApprovalDate(funding.getApprovalDate());
        dto.setStatus(funding.getStatus());
        dto.setRemarks(funding.getRemarks());

        return dto;
    }
}