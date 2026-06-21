package com.example.demo.service.impl;

import com.example.demo.dto.request.PublicationRequestDTO;
import com.example.demo.dto.response.PublicationResponseDTO;
import com.example.demo.entity.Project;
import com.example.demo.entity.Publication;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.PublicationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.PublicationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicationServiceImpl implements PublicationService {

    private final PublicationRepository publicationRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public PublicationServiceImpl(
            PublicationRepository publicationRepository,
            ProjectRepository projectRepository,
            UserRepository userRepository) {

        this.publicationRepository = publicationRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PublicationResponseDTO createPublication(PublicationRequestDTO request) {

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        User user = userRepository.findById(request.getCreatedById())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Publication publication = new Publication();

        publication.setTitle(request.getTitle());
        publication.setAbstractText(request.getAbstractText());
        publication.setKeywords(request.getKeywords());
        publication.setPublicationType(request.getPublicationType());
        publication.setJournalName(request.getJournalName());
        publication.setConferenceName(request.getConferenceName());
        publication.setPublicationDate(request.getPublicationDate());
        publication.setDoi(request.getDoi());
        publication.setStatus(request.getStatus());
        publication.setProject(project);
        publication.setCreatedBy(user);

        return mapToResponse(publicationRepository.save(publication));
    }

    @Override
    public PublicationResponseDTO getPublicationById(Long id) {

        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publication not found"));

        return mapToResponse(publication);
    }

    @Override
    public List<PublicationResponseDTO> getAllPublications() {

        return publicationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PublicationResponseDTO updatePublication(Long id, PublicationRequestDTO request) {

        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publication not found"));

        publication.setTitle(request.getTitle());
        publication.setAbstractText(request.getAbstractText());
        publication.setKeywords(request.getKeywords());
        publication.setPublicationType(request.getPublicationType());
        publication.setJournalName(request.getJournalName());
        publication.setConferenceName(request.getConferenceName());
        publication.setPublicationDate(request.getPublicationDate());
        publication.setDoi(request.getDoi());
        publication.setStatus(request.getStatus());

        return mapToResponse(publicationRepository.save(publication));
    }

    @Override
    public void deletePublication(Long id) {

        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publication not found"));

        publicationRepository.delete(publication);
    }

    private PublicationResponseDTO mapToResponse(Publication publication) {

        PublicationResponseDTO dto = new PublicationResponseDTO();

        dto.setId(publication.getId());
        dto.setTitle(publication.getTitle());
        dto.setAbstractText(publication.getAbstractText());
        dto.setKeywords(publication.getKeywords());
        dto.setPublicationType(publication.getPublicationType());
        dto.setJournalName(publication.getJournalName());
        dto.setConferenceName(publication.getConferenceName());
        dto.setPublicationDate(publication.getPublicationDate());
        dto.setDoi(publication.getDoi());
        dto.setStatus(publication.getStatus());

        return dto;
    }
    
    @Override
    public List<PublicationResponseDTO>
    searchPublications(String keyword) {

        return publicationRepository
                .findByTitleContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}