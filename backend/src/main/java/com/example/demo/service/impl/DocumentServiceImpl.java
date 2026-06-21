package com.example.demo.service.impl;

import com.example.demo.dto.response.DocumentResponseDTO;
import com.example.demo.entity.Document;
import com.example.demo.entity.Project;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.DocumentRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.service.DocumentService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final ProjectRepository projectRepository;

    public DocumentServiceImpl(DocumentRepository documentRepository,
                               ProjectRepository projectRepository) {
        this.documentRepository = documentRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public DocumentResponseDTO uploadFile(MultipartFile file,
                                          Long projectId) {

        try {

            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Project not found"));

            String uploadDir = System.getProperty("user.dir")
                    + File.separator
                    + "uploads"
                    + File.separator
                    + "documents"
                    + File.separator;

            File directory = new File(uploadDir);

            if (!directory.exists()) {
                directory.mkdirs();
            }

            String filePath = uploadDir + file.getOriginalFilename();

            File destinationFile = new File(filePath);

            file.transferTo(destinationFile);

            Document document = new Document();

            document.setFileName(file.getOriginalFilename());
            document.setFileType(file.getContentType());
            document.setFilePath(filePath);
            document.setProject(project);

            Document savedDocument =
                    documentRepository.save(document);

            return mapToResponse(savedDocument);

        } catch (Exception e) {
        	e.printStackTrace();
            throw new RuntimeException("File upload failed" + e.getMessage());
        }
    }

    @Override
    public List<DocumentResponseDTO> getAllDocuments() {

        return documentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteDocument(Long id) {

        Document document = documentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Document not found"));

        File file = new File(document.getFilePath());

        if (file.exists()) {
            file.delete();
        }

        documentRepository.delete(document);
    }
    
    @Override
    public Document downloadDocument(Long id) {

        return documentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Document not found"));
    }
    
    private DocumentResponseDTO mapToResponse(Document document) {

        DocumentResponseDTO dto =
                new DocumentResponseDTO();

        dto.setId(document.getId());
        dto.setFileName(document.getFileName());
        dto.setFileType(document.getFileType());

        return dto;
    }
}