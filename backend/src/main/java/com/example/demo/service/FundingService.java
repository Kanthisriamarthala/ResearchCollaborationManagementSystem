package com.example.demo.service;

import com.example.demo.dto.request.FundingRequestDTO;
import com.example.demo.dto.response.FundingResponseDTO;

import java.util.List;

public interface FundingService {

    FundingResponseDTO createFunding(FundingRequestDTO request);

    FundingResponseDTO getFundingById(Long id);

    List<FundingResponseDTO> getAllFunding();

    FundingResponseDTO updateFunding(Long id, FundingRequestDTO request);

    void deleteFunding(Long id);
}