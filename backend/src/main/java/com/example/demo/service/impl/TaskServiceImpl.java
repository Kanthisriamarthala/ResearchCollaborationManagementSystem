package com.example.demo.service.impl;

import com.example.demo.dto.request.TaskRequestDTO;
import com.example.demo.dto.response.TaskResponseDTO;
import com.example.demo.entity.Project;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(
            TaskRepository taskRepository,
            ProjectRepository projectRepository,
            UserRepository userRepository) {

        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO request) {

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found"));

        User user = userRepository.findById(request.getAssignedUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Task task = new Task();

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setStatus(request.getStatus());
        task.setDeadline(request.getDeadline());
        task.setProject(project);
        task.setAssignedUser(user);

        return mapToResponse(taskRepository.save(task));
    }

    @Override
    public TaskResponseDTO getTaskById(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found"));

        return mapToResponse(task);
    }

    @Override
    public List<TaskResponseDTO> getAllTasks() {

        return taskRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO request) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found"));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setStatus(request.getStatus());
        task.setDeadline(request.getDeadline());

        return mapToResponse(taskRepository.save(task));
    }

    @Override
    public void deleteTask(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found"));

        taskRepository.delete(task);
    }

    private TaskResponseDTO mapToResponse(Task task) {

        TaskResponseDTO dto = new TaskResponseDTO();

        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setPriority(task.getPriority());
        dto.setStatus(task.getStatus());
        dto.setDeadline(task.getDeadline());

        return dto;
    }
}