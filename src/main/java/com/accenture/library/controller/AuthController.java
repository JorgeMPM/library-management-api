package com.accenture.library.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.library.dto.ErrorResponseDTO;
import com.accenture.library.dto.LoginRequestDTO;
import com.accenture.library.dto.LoginResponseDTO;
import com.accenture.library.security.JwtService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/*
 * Crea el @RestController AuthController con @RequestMapping("/auth") y @Tag(name = "Autenticación"), con campos final
 * AuthenticationManager y JwtService inyectados por constructor.
 * POST /login con @Valid @RequestBody LoginRequestDTO: autentica con UsernamePasswordAuthenticationToken; de las authorities
 * del resultado toma la que empieza por "ROLE_"; genera el token con jwtService y devuelve 200 con LoginResponseDTO.
 * @SecurityRequirements vacío y @ApiResponses 200, 400 y 401 (400 y 401 con schema ErrorResponseDTO).
 * Javadoc en español en la clase, el constructor y el método. — generado por IA
 */
/**
 * Endpoint público de autenticación: devuelve un token JWT si las credenciales son correctas.
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Inicio de sesión y obtención del token JWT")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    /**
     * Crea el controller con el gestor de autenticación y el servicio de JWT.
     *
     * @param authenticationManager gestor que comprueba usuario y contraseña
     * @param jwtService            servicio de generación de tokens
     */
    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /**
     * Comprueba las credenciales y genera el token con el rol del usuario.
     *
     * @param dto credenciales
     * @return 200 con el token
     */
    @PostMapping("/login")
    @SecurityRequirements
    @Operation(summary = "Iniciar sesión", description = "Devuelve un token JWT válido durante 24 horas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login correcto"),
            @ApiResponse(responseCode = "400", description = "Falta el usuario o la contraseña",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.username(), dto.password()));
        String role = null;
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (authority.getAuthority().startsWith("ROLE_")) {
                role = authority.getAuthority();
                break;
            }
        }
        String token = jwtService.generateToken(authentication.getName(), role);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
