package com.example.demo.controller;

import com.example.demo.common.ApiResponse;
import com.example.demo.dto.request.TaskRequestDTO;
import com.example.demo.dto.response.TaskResponseDTO;
import com.example.demo.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin("*")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ApiResponse<TaskResponseDTO> createTask(
            @RequestBody TaskRequestDTO request) {

        return new ApiResponse<>(
                true,
                "Task created successfully",
                taskService.createTask(request)
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<TaskResponseDTO> getTaskById(
            @PathVariable Long id) {

        return new ApiResponse<>(
                true,
                "Task fetched successfully",
                taskService.getTaskById(id)
        );
    }

    @GetMapping
    public ApiResponse<List<TaskResponseDTO>> getAllTasks() {

        return new ApiResponse<>(
                true,
                "Tasks fetched successfully",
                taskService.getAllTasks()
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<TaskResponseDTO> updateTask(
            @PathVariable Long id,
            @RequestBody TaskRequestDTO request) {

        return new ApiResponse<>(
                true,
                "Task updated successfully",
                taskService.updateTask(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteTask(
            @PathVariable Long id) {

        taskService.deleteTask(id);

        return new ApiResponse<>(
                true,
                "Task deleted successfully",
                null
        );
    }
}