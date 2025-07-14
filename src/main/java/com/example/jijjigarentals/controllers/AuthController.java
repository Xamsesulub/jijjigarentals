package com.example.jijjigarentals.controllers;

import com.example.jijjigarentals.config.JwtUtil;
import com.example.jijjigarentals.dtos.JwtResponse;
import com.example.jijjigarentals.dtos.LoginRequest;
import com.example.jijjigarentals.dtos.RegisterRequest;
import com.example.jijjigarentals.models.User;
import com.example.jijjigarentals.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public JwtResponse register(@RequestBody RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        User saved = userRepository.save(user);
        String token = jwtUtil.generateToken(saved.getId(), saved.getRole());
        return new JwtResponse(token);
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Feil e-post"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Feil passord");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getRole());
        return new JwtResponse(token);
    }
}