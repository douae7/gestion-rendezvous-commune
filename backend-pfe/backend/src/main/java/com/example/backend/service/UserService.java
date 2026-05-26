package com.example.backend.service;

import com.example.backend.dto.PasswordDTO;
import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ALL USERS
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // BY ROLE
    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    // BY ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // UPDATE
    public User updateUser(Long id, User updatedUser) {

        User user = getUserById(id);

        user.setName(updatedUser.getName());
        user.setPhone(updatedUser.getPhone());
        user.setAddress(updatedUser.getAddress());
        user.setCity(updatedUser.getCity());

        return userRepository.save(user);
    }

    // PASSWORD
    public void changePassword(Long id, PasswordDTO dto) {

        if (dto.getCurrentPassword() == null || dto.getNewPassword() == null) {
            throw new IllegalArgumentException("Password fields cannot be null");
        }

        User user = getUserById(id);

        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("Current password incorrect");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }
}