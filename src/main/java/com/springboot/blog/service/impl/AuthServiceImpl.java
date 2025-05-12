package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.payload.LoginDTO;
import com.springboot.blog.payload.RegisterDTO;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.JwtTokenProvider;
import com.springboot.blog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

//Implementation class for AuthService Interface
@Service
public class AuthServiceImpl implements AuthService {
    private AuthenticationManager authenticationManager;  // Used for authenticating the user
    private UserRepository userRepository;  // Repository for User entity
    private RoleRepository roleRepository;  // Repository for Role entity
    private PasswordEncoder passwordEncoder;  // Password encoding to store hashed passwords
    private JwtTokenProvider jwtTokenProvider;  // Provider to generate JWT token for authenticated users

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // =================================================================================================================================

    // Method to handle user login and return a JWT token
    @Override
    public String login(LoginDTO loginDTO) {
        // Authenticate the user using username or email and password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword())
        );

        // Set the authentication context for the current session
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate and return JWT token
        String token = jwtTokenProvider.generateToken(authentication);
        return token;
    }

    // =================================================================================================================================

    // Method to handle user registration
    @Override
    public String register(RegisterDTO registerDTO) {

        // Check if username already exists in the database
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Username Already exists");
        }

        // Check if email already exists in the database
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Email Already exists");
        }

        // Create a new user object from the registerDTO data
        User user = new User();
        user.setName(registerDTO.getName());
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));  // Encode password before saving

        // Assign roles to the user
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();  // Get default user role from the repository
        roles.add(userRole);
        user.setRoles(roles);

        // Save the new user to the database
        userRepository.save(user);

        // Return success message after successful registration
        return "User registered Successfully";
    }
}