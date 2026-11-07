package com.accenture.library.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * Crea el @Component JwtAuthenticationFilter que extiende OncePerRequestFilter, con un campo final JwtService inyectado por constructor.
 * doFilterInternal: lee la cabecera Authorization; si empieza por "Bearer " y jwtService.isTokenValid(token), crea un
 * UsernamePasswordAuthenticationToken con el usuario, credenciales null y una SimpleGrantedAuthority con el rol del token,
 * y lo guarda en SecurityContextHolder. Siempre continúa con filterChain.doFilter.
 * Javadoc en español en la clase, el constructor y el método. — generado por IA
 */
/**
 * Filtro que lee el token JWT de la cabecera Authorization y, si es válido, autentica la petición.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    /**
     * Crea el filtro con el servicio de JWT.
     *
     * @param jwtService servicio de generación y validación de tokens
     */
    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    /**
     * Si la petición trae un token Bearer válido, guarda la autenticación en el contexto de seguridad.
     * Si no lo trae o es inválido, la petición sigue sin autenticar y Spring Security responderá 401 en las rutas protegidas.
     *
     * @param request     petición HTTP
     * @param response    respuesta HTTP
     * @param filterChain cadena de filtros
     * @throws ServletException si falla el filtro siguiente
     * @throws IOException      si falla la escritura de la respuesta
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (jwtService.isTokenValid(token)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        jwtService.extractUsername(token), null,
                        List.of(new SimpleGrantedAuthority(jwtService.extractRole(token))));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
