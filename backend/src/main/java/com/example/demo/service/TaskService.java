package com.example.demo.service;

import com.example.demo.dto.request.TaskRequestDTO;
import com.example.demo.dto.response.TaskResponseDTO;

import java.util.List;

public interface TaskService {

    TaskResponseDTO createTask(TaskRequestDTO request);

    TaskResponseDTO getTaskById(Long id);

    List<TaskResponseDTO> getAllTasks();

    TaskResponseDTO updateTask(Long id, TaskRequestDTO request);

    void deleteTask(Long id);
}