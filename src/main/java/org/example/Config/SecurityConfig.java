package org.example.Config;


import lombok.RequiredArgsConstructor;
import org.example.JWT.JWTAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                .requestMatchers(HttpMethod.POST, "/sneaks/usuario/registrarUsuario").permitAll()
                                .requestMatchers(HttpMethod.POST, "/sneaks/usuario/login").permitAll()
                                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                                .requestMatchers(HttpMethod.GET, "/sneaks/categoria/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/sneaks/talle/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/sneaks/precio/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/sneaks/imagen/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/sneaks/detalle/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/sneaks/descuento/**").permitAll()
                                .anyRequest().authenticated()
                ).sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
