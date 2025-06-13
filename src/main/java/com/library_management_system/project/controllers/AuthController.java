package com.library_management_system.project.controllers;

import com.library_management_system.project.dto.LoginRequestDto;
import com.library_management_system.project.dto.LoginResponseDto;
import com.library_management_system.project.dto.RegisterRequestDto;
import com.library_management_system.project.entities.User;
import com.library_management_system.project.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registernormaluser")
    public ResponseEntity<User> registerNormalUser(@RequestBody RegisterRequestDto registerRequestDto){
        return ResponseEntity.ok(authService.registerNormalUser(registerRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }
}
