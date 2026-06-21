package com.example.demo.controller;

import com.example.demo.common.ApiResponse;
import com.example.demo.dto.response.DashboardResponseDTO;
import com.example.demo.service.DashboardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin("*")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(
            DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    public ApiResponse<DashboardResponseDTO> getSummary() {

        return new ApiResponse<>(
                true,
                "Dashboard data fetched successfully",
                dashboardService.getSummary()
        );
    }
}