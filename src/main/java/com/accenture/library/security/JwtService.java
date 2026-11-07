package com.accenture.library.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
/*
 * Crea el @Service JwtService que recibe por constructor @Value("${jwt.secret}") String secret y
 * @Value("${jwt.expiration-ms}") long expirationMs, y crea una SecretKey con Keys.hmacShaKeyFor(secret en UTF-8).
 * generateToken(String username, String role): token JJWT con subject, claim "role", issuedAt, expiration y signWith(key).
 * extractUsername(String token) y extractRole(String token) leyendo los claims con Jwts.parser().verifyWith(key).
 * isTokenValid(String token): true si se puede parsear, false si lanza JwtException o IllegalArgumentException.
 * Javadoc en español en la clase, el constructor y cada método. — generado por IA
 */
/**
 * Clase de servicio para la gestión de tokens JWT.
 * Proporciona métodos para generar, extraer información y validar tokens JWT.
 */
@Service
public class JwtService {

    private final SecretKey key;
    private final long expirationMs;

    /**
     * Crea el servicio con la clave secreta y la caducidad leídas de application.properties.
     *
     * @param secret       clave secreta de al menos 32 caracteres
     * @param expirationMs caducidad del token en milisegundos
     */
    public JwtService(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration-ms}") long expirationMs) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    /**
     * Genera un token con el usuario (sub), el rol, la fecha de emisión (iat) y la de caducidad (exp).
     *
     * @param username nombre de usuario
     * @param role     rol del usuario, por ejemplo ROLE_ADMIN
     * @return el token firmado
     */
    public String generateToken(String username, String role) {
        Date now = new Date();
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expirationMs))
                .signWith(key)
                .compact();
    }

    /**
     * Extrae el usuario (sub) de un token.
     *
     * @param token token JWT
     * @return el nombre de usuario
     */
    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    /**
     * Extrae el rol de un token.
     *
     * @param token token JWT
     * @return el rol, por ejemplo ROLE_ADMIN
     */
    public String extractRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    /**
     * Comprueba que el token tiene una firma correcta y no ha caducado.
     *
     * @param token token JWT
     * @return true si es válido
     */
    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * Verifica la firma del token y devuelve sus claims.
     *
     * @param token token JWT
     * @return los claims del token
     */
    private Claims parseClaims(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }
}