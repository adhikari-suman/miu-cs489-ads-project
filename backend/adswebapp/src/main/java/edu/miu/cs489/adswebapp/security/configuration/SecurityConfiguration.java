package edu.miu.cs489.adswebapp.security.configuration;

import edu.miu.cs489.adswebapp.respository.UserRepository;
import edu.miu.cs489.adswebapp.security.filter.JwtFilter;
import edu.miu.cs489.adswebapp.security.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration implements WebMvcConfigurer {
    private final JwtFilter              jwtFilter;
    private final AuthenticationProvider authenticationProvider;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(CsrfConfigurer::disable)
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers("/api/v1/auth/current-user")
                                                                         .authenticated()
                                                                         .requestMatchers("/api/v1/auth/*")
                                                                         .permitAll()
                                                                         .requestMatchers(
                                                                                 HttpMethod.DELETE,
                                                                                 "/api/v1/patients/{patientId}"
                                                                                         )
                                                                         .hasRole(Role.OFFICE_ADMIN.name())
                                                                         .requestMatchers(
                                                                                 "/api/v1/patients/**",
                                                                                 "/api/v1/addresses/**"
                                                                                         )
                                                                         .hasAnyRole(
                                                                                 Role.PATIENT.name(),
                                                                                 Role.OFFICE_ADMIN.name(),
                                                                                 Role.DENTIST.name()
                                                                                    )
                                                                         .anyRequest()
                                                                         .authenticated())
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .authenticationProvider(authenticationProvider)
            .sessionManagement(
                    httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
