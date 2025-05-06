package com.springboot.blog.controller;

import com.springboot.blog.payload.JwtAthResponse;
import com.springboot.blog.payload.LoginDTO;
import com.springboot.blog.payload.RegisterDTO;
import com.springboot.blog.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(
            summary = "Login REST API",
            description = "Authenticates a user and returns a JWT token if credentials are valid."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Http Status 200 OK - Successful login"),
            @ApiResponse(responseCode = "401", description = "Http Status 401 UNAUTHORIZED - Invalid credentials")
    })
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JwtAthResponse> login(@RequestBody LoginDTO loginDTO){
        String token = authService.login(loginDTO);
        JwtAthResponse jwtAthResponse = new JwtAthResponse();
        jwtAthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAthResponse);
    }

    //Build Register REST API
    @Operation(
            summary = "Register REST API",
            description = "Registers a new user and returns a success message."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Http Status 201 CREATED - User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Http Status 400 BAD REQUEST - Invalid or duplicate data")
    })
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
        String response = authService.register(registerDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
