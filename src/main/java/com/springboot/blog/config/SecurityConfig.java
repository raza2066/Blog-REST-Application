package com.springboot.blog.config;

import com.springboot.blog.security.JwtAuthenticationEntryPoint;
import com.springboot.blog.security.JwtAuthenticationFilter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//  flow of spring security database authentication
//	Client Request → Security Filter Chain →
//	If Public → Permit
//	Else →
//	AuthenticationManager →
//	CustomUserDetailsService → Load from DB →
//	Check password with BCrypt →
//	If match → Authenticated →
//	Check authorization →
//	If role allowed → Proceed
//	Else → 403 Forbidden

@Configuration                            // Marks this class as a source of bean definitions for the application context
@EnableMethodSecurity                    // Enables method-level security annotations, like @PreAuthorize
@SecurityScheme(                        // Configures the security scheme for Swagger UI, indicating JWT authentication
		name = "Bear Authentication",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
)
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService; // Injecting the UserDetailsService bean for loading user details

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint; // Custom entry point to handle unauthorized access

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter; // Custom filter to validate JWT token in requests

//	=================================================================================================================================

	@Bean
	public static PasswordEncoder passwordEncoder() {
		// Returns a password encoder instance (BCrypt in this case) for encoding and matching passwords
		return new BCryptPasswordEncoder();
	}

//	=================================================================================================================================

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		// Returns an AuthenticationManager bean to authenticate requests
		return configuration.getAuthenticationManager();
	}

//	=================================================================================================================================

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// Configures the HTTP security for the application, including authorization and session management

		http.csrf(csrf -> csrf.disable()) // Disables CSRF protection (stateless API)
				.authorizeHttpRequests((authorize) ->
						    // Defines authorization rules for HTTP requests
							authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll() // Allow public access to GET requests under /api/**
									.requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll() // Allow public access to POST requests under /api/auth/**
									.requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll() // Allow public access to Swagger UI
									.requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll() // Allow public access to API documentation
									.anyRequest().authenticated() // Require authentication for all other requests
				)
//				.httpBasic(Customizer.withDefaults()

				.exceptionHandling(exception -> exception
						.authenticationEntryPoint(jwtAuthenticationEntryPoint) // Custom entry point for unauthorized access
				)
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Ensures the session is stateless (no session storage)
				);

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Adds the JWT filter before the standard authentication filter
		return http.build(); // Builds the security filter chain
	}

//	=================================================================================================================================

//In memory authentication
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails raza = User.builder().username("Raza").password(passwordEncoder().encode("Raza")).roles("USER")
//				.build();
//
//		UserDetails admin = User.builder().username("Admin").password(passwordEncoder().encode("Admin")).roles("ADMIN")
//				.build();
//
//		return new InMemoryUserDetailsManager(raza, admin);
//	}
}
