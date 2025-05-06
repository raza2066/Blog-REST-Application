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

@Configuration
@EnableMethodSecurity
@SecurityScheme(
		name = "Bear Authentication",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
)
public class SecurityConfig {
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

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests((authorize) ->
									//				authorize.anyRequest().authenticated()
											authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
													.requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
													.requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
													.requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()
													.anyRequest().authenticated()
				)
//				.httpBasic(Customizer.withDefaults()
				.exceptionHandling(exception->exception
								.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				).sessionManagement(session->session
								.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				);

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

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
