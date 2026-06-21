package com.example.demo.controller;

import com.example.demo.common.ApiResponse;
import com.example.demo.dto.response.DocumentResponseDTO;
import com.example.demo.service.DocumentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Document;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.File;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin("*")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(
            DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/upload")
    public ApiResponse<DocumentResponseDTO> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam Long projectId) {

        return new ApiResponse<>(
                true,
                "Document uploaded successfully",
                documentService.uploadFile(file, projectId)
        );
    }

    @GetMapping
    public ApiResponse<List<DocumentResponseDTO>> getAllDocuments() {

        return new ApiResponse<>(
                true,
                "Documents fetched successfully",
                documentService.getAllDocuments()
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteDocument(
            @PathVariable Long id) {

        documentService.deleteDocument(id);

        return new ApiResponse<>(
                true,
                "Document deleted successfully",
                null
        );
    }
    
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadDocument(
            @PathVariable Long id) {

        Document document =
                documentService.downloadDocument(id);

        File file = new File(document.getFilePath());

        Resource resource =
                new FileSystemResource(file);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                document.getFileName() + "\"")
                .body(resource);
    }
}