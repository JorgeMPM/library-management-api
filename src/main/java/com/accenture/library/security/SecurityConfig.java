package com.accenture.library.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

/*
 * Crea la clase @Configuration @EnableWebSecurity SecurityConfig con campos final JwtAuthenticationFilter y
 * HandlerExceptionResolver (@Qualifier("handlerExceptionResolver")) inyectados por constructor.
 * Bean SecurityFilterChain: csrf desactivado, cors(Customizer.withDefaults()), sesión STATELESS
 * permitAll en POST /auth/login, /swagger-ui.html, /swagger-ui/**, /v3/api-docs/** y /error
 * DELETE /api/v1/books/*, /api/v1/authors/* y /api/v1/categories/* solo hasRole("ADMIN"); el resto authenticated
 * authenticationEntryPoint y accessDeniedHandler delegan en resolver.resolveException; el filtro JWT antes de UsernamePasswordAuthenticationFilter.
 * Beans: PasswordEncoder BCrypt; UserDetailsService InMemoryUserDetailsManager con admin/admin123 rol ADMIN y user/user123 rol USER,
 * marcado con // TODO: en producción esto debe venir de base de datos; AuthenticationManager desde AuthenticationConfiguration
 * CorsConfigurationSource del enunciado. Javadoc en español en la clase, el constructor y cada método. — generado por IA
 */
/**
 * Configuración de Spring Security: autenticación JWT sin sesión, rutas públicas, permisos por rol y CORS.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final HandlerExceptionResolver resolver;

    /**
     * Crea la configuración con el filtro JWT y el resolutor de excepciones de Spring MVC.
     *
     * @param jwtAuthenticationFilter filtro que autentica las peticiones con token
     * @param resolver                resolutor que entrega los errores 401 y 403 al GlobalExceptionHandler
     */
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.resolver = resolver;
    }

    /**
     * Define las reglas de seguridad de la API.
     *
     * @param http configurador de seguridad HTTP
     * @return la cadena de filtros de seguridad
     * @throws Exception si falla la configuración
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/books/*", "/api/v1/authors/*",
                                "/api/v1/categories/*").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                resolver.resolveException(request, response, null, authException))
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                resolver.resolveException(request, response, null, accessDeniedException)))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Codificador de contraseñas BCrypt.
     *
     * @return el codificador
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Usuarios de la API definidos en memoria.
     *
     * @param passwordEncoder codificador de contraseñas
     * @return el servicio de usuarios
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // TODO: en producción esto debe venir de base de datos
        return new InMemoryUserDetailsManager(
                User.withUsername("admin").password(passwordEncoder.encode("admin123")).roles("ADMIN").build(),
                User.withUsername("user").password(passwordEncoder.encode("user123")).roles("USER").build());
    }

    /**
     * Gestor de autenticación que usa el login para comprobar usuario y contraseña.
     *
     * @param configuration configuración de autenticación de Spring
     * @return el gestor de autenticación
     * @throws Exception si no se puede obtener
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Configuración CORS para que el frontal de prueba pueda llamar a la API (solo para desarrollo).
     *
     * @return la fuente de configuración CORS
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}