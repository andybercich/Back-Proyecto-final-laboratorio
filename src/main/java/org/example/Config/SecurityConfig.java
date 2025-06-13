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
                                .requestMatchers(HttpMethod.GET, "/sneaks/producto/**").permitAll()

                                .requestMatchers(HttpMethod.POST, "/sneaks/categoria/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/sneaks/categoria/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/sneaks/categoria/**").hasAuthority("ADMIN")

                                .requestMatchers(HttpMethod.POST, "/sneaks/talle/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/sneaks/talle/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/sneaks/talle/**").hasAuthority("ADMIN")

                                .requestMatchers(HttpMethod.POST, "/sneaks/precio/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/sneaks/precio/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/sneaks/precio/**").hasAuthority("ADMIN")

                                .requestMatchers(HttpMethod.POST, "/sneaks/imagen/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/sneaks/imagen/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/sneaks/imagen/**").hasAuthority("ADMIN")

                                .requestMatchers(HttpMethod.POST, "/sneaks/detalle/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/sneaks/detalle/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/sneaks/detalle/**").hasAuthority("ADMIN")

                                .requestMatchers(HttpMethod.POST, "/sneaks/descuento/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/sneaks/descuento/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/sneaks/descuento/**").hasAuthority("ADMIN")

                                .requestMatchers(HttpMethod.POST, "/sneaks/producto/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/sneaks/producto/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/sneaks/producto/**").hasAuthority("ADMIN")

                                .requestMatchers(HttpMethod.GET, "/sneaks/ordenCompra/**").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/sneaks/ordenCompra/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/sneaks/ordenCompra/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/sneaks/ordenCompraDetalle/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/sneaks/ordenCompraDetalle/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/sneaks/ordenCompraDetalle").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/sneaks/ordenCompra").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/sneaks/usuario").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/sneaks/direccion").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/sneaks/usuario/admin").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/sneaks/usuario/admin").hasAuthority("ADMIN")


                                .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
