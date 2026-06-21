package com.example.demo.service.impl;

import com.example.demo.dto.request.UserRequestDTO;
import com.example.demo.dto.response.UserResponseDTO;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO request) {

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setDepartment(request.getDepartment());
        user.setDesignation(request.getDesignation());
        user.setResearchInterests(request.getResearchInterests());

        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    @Override
    public UserResponseDTO getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        return mapToResponse(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO request) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setDepartment(request.getDepartment());
        user.setDesignation(request.getDesignation());
        user.setResearchInterests(request.getResearchInterests());

        User updatedUser = userRepository.save(user);

        return mapToResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        userRepository.delete(user);
    }

    private UserResponseDTO mapToResponse(User user) {

        UserResponseDTO dto = new UserResponseDTO();

        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setDepartment(user.getDepartment());
        dto.setDesignation(user.getDesignation());
        dto.setResearchInterests(user.getResearchInterests());

        return dto;
    }
    
    @Override
    public List<UserResponseDTO> searchUsers(
            String keyword) {

    	return userRepository
    	        .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
    	                keyword,
    	                keyword
    	        )
    	        .stream()
    	        .map(this::mapToResponse)
    	        .toList();
    }
}