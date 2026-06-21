package com.example.demo.service;

import com.example.demo.dto.response.DocumentResponseDTO;
import com.example.demo.entity.Document;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {

    DocumentResponseDTO uploadFile(
            MultipartFile file,
            Long projectId);

    List<DocumentResponseDTO> getAllDocuments();

    void deleteDocument(Long id);
    
    Document downloadDocument(Long id);
}