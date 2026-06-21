package com.example.demo.controller;

import com.example.demo.common.ApiResponse;
import com.example.demo.dto.request.PublicationRequestDTO;
import com.example.demo.dto.response.PublicationResponseDTO;
import com.example.demo.service.PublicationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publications")
@CrossOrigin("*")
public class PublicationController {

    private final PublicationService publicationService;

    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @PostMapping
    public ApiResponse<PublicationResponseDTO> createPublication(
            @RequestBody PublicationRequestDTO request) {

        return new ApiResponse<>(
                true,
                "Publication created successfully",
                publicationService.createPublication(request)
        );
    }

    @GetMapping
    public ApiResponse<List<PublicationResponseDTO>> getAllPublications() {

        return new ApiResponse<>(
                true,
                "Publications fetched successfully",
                publicationService.getAllPublications()
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<PublicationResponseDTO> getPublicationById(
            @PathVariable Long id) {

        return new ApiResponse<>(
                true,
                "Publication fetched successfully",
                publicationService.getPublicationById(id)
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<PublicationResponseDTO> updatePublication(
            @PathVariable Long id,
            @RequestBody PublicationRequestDTO request) {

        return new ApiResponse<>(
                true,
                "Publication updated successfully",
                publicationService.updatePublication(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deletePublication(
            @PathVariable Long id) {

        publicationService.deletePublication(id);

        return new ApiResponse<>(
                true,
                "Publication deleted successfully",
                null
        );
    }
    
    @GetMapping("/search")
    public ApiResponse<List<PublicationResponseDTO>>
    searchPublications(
            @RequestParam String keyword) {

        return new ApiResponse<>(
                true,
                "Publications fetched successfully",
                publicationService.searchPublications(keyword)
        );
    }
}