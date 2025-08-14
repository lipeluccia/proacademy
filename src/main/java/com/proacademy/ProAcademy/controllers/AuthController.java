package com.proacademy.proacademy.controllers;

import com.proacademy.proacademy.dtos.LoginRequestDTO;
import com.proacademy.proacademy.dtos.TokenResponseDTO;
import com.proacademy.proacademy.models.User;
import com.proacademy.proacademy.models.enums.ProfileEnum;
import com.proacademy.proacademy.repositories.UserRepository;
import com.proacademy.proacademy.security.JWTUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          JWTUtil jwtUtil,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
            String token = jwtUtil.generateToken(user.getEmail());
            return ResponseEntity.ok(new TokenResponseDTO(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Email ou senha inválidos");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.addProfile(ProfileEnum.USER);
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }
}
