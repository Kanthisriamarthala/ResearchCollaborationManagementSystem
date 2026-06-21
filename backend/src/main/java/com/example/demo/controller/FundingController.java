package com.example.demo.controller;

import com.example.demo.common.ApiResponse;
import com.example.demo.dto.request.FundingRequestDTO;
import com.example.demo.dto.response.FundingResponseDTO;
import com.example.demo.service.FundingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funding")
@CrossOrigin("*")
public class FundingController {

    private final FundingService fundingService;

    public FundingController(FundingService fundingService) {
        this.fundingService = fundingService;
    }

    @PostMapping
    public ApiResponse<FundingResponseDTO> createFunding(
            @RequestBody FundingRequestDTO request) {

        return new ApiResponse<>(
                true,
                "Funding created successfully",
                fundingService.createFunding(request)
        );
    }

    @GetMapping
    public ApiResponse<List<FundingResponseDTO>> getAllFunding() {

        return new ApiResponse<>(
                true,
                "Funding records fetched successfully",
                fundingService.getAllFunding()
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<FundingResponseDTO> getFundingById(
            @PathVariable Long id) {

        return new ApiResponse<>(
                true,
                "Funding fetched successfully",
                fundingService.getFundingById(id)
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<FundingResponseDTO> updateFunding(
            @PathVariable Long id,
            @RequestBody FundingRequestDTO request) {

        return new ApiResponse<>(
                true,
                "Funding updated successfully",
                fundingService.updateFunding(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteFunding(
            @PathVariable Long id) {

        fundingService.deleteFunding(id);

        return new ApiResponse<>(
                true,
                "Funding deleted successfully",
                null
        );
    }
}