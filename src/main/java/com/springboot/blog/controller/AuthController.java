package com.springboot.blog.controller;

import com.springboot.blog.payload.JwtAthResponse;
import com.springboot.blog.payload.LoginDTO;
import com.springboot.blog.payload.RegisterDTO;
import com.springboot.blog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //Build Login REST API
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JwtAthResponse> login(@RequestBody LoginDTO loginDTO){
        String token = authService.login(loginDTO);
        JwtAthResponse jwtAthResponse = new JwtAthResponse();
        jwtAthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAthResponse);
    }

    //Build Register REST API
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
        String response = authService.register(registerDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
