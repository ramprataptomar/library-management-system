package com.library_management_system.project.services;

import com.library_management_system.project.dto.LoginRequestDto;
import com.library_management_system.project.dto.LoginResponseDto;
import com.library_management_system.project.dto.RegisterRequestDto;
import com.library_management_system.project.entities.User;
import com.library_management_system.project.jwt.JwtService;
import com.library_management_system.project.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public User registerNormalUser(RegisterRequestDto registerRequestDto) {
        if(userRepository.findByName(registerRequestDto.getName()).isPresent()){
            throw new RuntimeException("User already exist");
        }

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");

        User user = new User();
        user.setName(registerRequestDto.getName());
        user.setEmail(registerRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getName(), loginRequestDto.getPassword()));

        User user = userRepository.findByName(loginRequestDto.getName())
                .orElseThrow(() -> new RuntimeException("User already exist"));

        String token = jwtService.generateToken(user);

        return LoginResponseDto.builder()
                .token(token)
                .name(user.getName())
                .roles(user.getRoles())
                .build();
    }

    public User registerAdminUser(RegisterRequestDto registerRequestDto) {
        if(userRepository.findByName(registerRequestDto.getName()).isPresent()){
            throw new RuntimeException("User already exist");
        }

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_ADMIN");
        roles.add("ROLE_USER");

        User user = new User();
        user.setName(registerRequestDto.getName());
        user.setEmail(registerRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        user.setRoles(roles);

        return userRepository.save(user);
    }
}
