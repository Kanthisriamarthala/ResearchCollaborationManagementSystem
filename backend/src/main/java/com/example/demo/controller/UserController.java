package com.example.demo.controller;

import com.example.demo.common.ApiResponse;
import com.example.demo.dto.request.UserRequestDTO;
import com.example.demo.dto.response.UserResponseDTO;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ApiResponse<UserResponseDTO> createUser(
            @Valid @RequestBody UserRequestDTO request) {

        return new ApiResponse<>(
                true,
                "User created successfully",
                userService.createUser(request)
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponseDTO> getUserById(
            @PathVariable Long id) {

        return new ApiResponse<>(
                true,
                "User fetched successfully",
                userService.getUserById(id)
        );
    }

    @GetMapping
    public ApiResponse<List<UserResponseDTO>> getAllUsers() {

        return new ApiResponse<>(
                true,
                "Users fetched successfully",
                userService.getAllUsers()
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDTO request) {

        return new ApiResponse<>(
                true,
                "User updated successfully",
                userService.updateUser(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);

        return new ApiResponse<>(
                true,
                "User deleted successfully",
                null
        );
    }
    
    @GetMapping("/search")
    public ApiResponse<List<UserResponseDTO>>
    searchUsers(
            @RequestParam String keyword) {

        return new ApiResponse<>(
                true,
                "Users fetched successfully",
                userService.searchUsers(keyword)
        );
    }
}