package com.example.demo.service;

import com.example.demo.dto.request.PublicationRequestDTO;
import com.example.demo.dto.response.PublicationResponseDTO;

import java.util.List;

public interface PublicationService {

    PublicationResponseDTO createPublication(PublicationRequestDTO request);

    PublicationResponseDTO getPublicationById(Long id);

    List<PublicationResponseDTO> getAllPublications();

    PublicationResponseDTO updatePublication(Long id, PublicationRequestDTO request);

    void deletePublication(Long id);
    
    List<PublicationResponseDTO>
    searchPublications(String keyword);
}