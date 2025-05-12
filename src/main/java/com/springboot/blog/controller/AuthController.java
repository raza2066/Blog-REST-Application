package com.springboot.blog.controller;

import com.springboot.blog.payload.JwtAthResponse;
import com.springboot.blog.payload.LoginDTO;
import com.springboot.blog.payload.RegisterDTO;
import com.springboot.blog.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// Controller For Authentication Resources
@RestController // Marks this class as a REST controller
@RequestMapping("/api/auth") // Maps all endpoints in this controller to the /api/auth path
@Tag(
        name = "CRUD REST APIs for Authentication Resources" // Swagger tag to group authentication-related APIs
)
public class AuthController {
    private AuthService authService; // Injects the authentication service for login and registration functionality

    public AuthController(AuthService authService) {
        // Constructor-based dependency injection for AuthService
        this.authService = authService;
    }

//    =================================================================================================================================

    //Login REST API
    @Operation(
            summary = "Login REST API", // Swagger operation summary for the login API
            description = "Authenticates a user and returns a JWT token if credentials are valid." // Description of what this API does
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Http Status 200 OK - Successful login"), // Response code for successful login
            @ApiResponse(responseCode = "401", description = "Http Status 401 UNAUTHORIZED - Invalid credentials") // Response code for invalid login attempt
    })
    @PostMapping(value = {"/login", "/signin"}) // Accepts POST requests to /login or /signin paths
    public ResponseEntity<JwtAthResponse> login(@RequestBody LoginDTO loginDTO){
        String token = authService.login(loginDTO); // Calls AuthService to authenticate and generate the JWT token
        JwtAthResponse jwtAthResponse = new JwtAthResponse(); // Creates a response object containing the JWT token
        jwtAthResponse.setAccessToken(token); // Sets the generated token in the response
        return ResponseEntity.ok(jwtAthResponse); // Returns HTTP 200 OK along with the JWT token in the response body
    }

//    =================================================================================================================================

    //Register REST API
    @Operation(
            summary = "Register REST API", // Swagger operation summary for the registration API
            description = "Registers a new user and returns a success message." // Description of what this API does
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Http Status 201 CREATED - User registered successfully"), // Response code for successful registration
            @ApiResponse(responseCode = "400", description = "Http Status 400 BAD REQUEST - Invalid or duplicate data") // Response code for failed registration due to invalid data
    })
    @PostMapping(value = {"/register", "/signup"}) // Accepts POST requests to /register or /signup paths
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
        String response = authService.register(registerDTO); // Calls AuthService to register a new user
        return new ResponseEntity<>(response, HttpStatus.CREATED); // Returns HTTP 201 CREATED along with a success message
    }

}
