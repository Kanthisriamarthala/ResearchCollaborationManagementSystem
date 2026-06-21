package com.example.demo.service.impl;

import com.example.demo.dto.response.DashboardResponseDTO;
import com.example.demo.enums.TaskStatus;
import com.example.demo.repository.*;
import com.example.demo.service.DashboardService;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final PublicationRepository publicationRepository;
    private final FundingRepository fundingRepository;

    public DashboardServiceImpl(
            UserRepository userRepository,
            ProjectRepository projectRepository,
            TaskRepository taskRepository,
            PublicationRepository publicationRepository,
            FundingRepository fundingRepository) {

        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.publicationRepository = publicationRepository;
        this.fundingRepository = fundingRepository;
    }

    @Override
    public DashboardResponseDTO getSummary() {

        DashboardResponseDTO dto = new DashboardResponseDTO();

        dto.setTotalUsers(userRepository.count());
        dto.setTotalProjects(projectRepository.count());
        dto.setTotalTasks(taskRepository.count());
        dto.setCompletedTasks(
                taskRepository.countByStatus(TaskStatus.COMPLETED)
        );
        dto.setTotalPublications(publicationRepository.count());
        dto.setTotalFundingRecords(fundingRepository.count());

        return dto;
    }
}