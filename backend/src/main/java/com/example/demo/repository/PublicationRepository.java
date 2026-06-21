package com.example.demo.repository;

import com.example.demo.entity.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicationRepository extends JpaRepository<Publication, Long> {

    List<Publication> findByProjectId(Long projectId);
    
    List<Publication> findByTitleContainingIgnoreCase(
            String keyword);

}