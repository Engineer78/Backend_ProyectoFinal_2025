package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.security.util;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Usuario;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Clase utilitaria para generar y validar tokens JWT utilizando la biblioteca jjwt.
 * Esta clase incluye métodos para crear tokens, extraer claims, validar tokens
 * y verificar el estado de expiración.
 */
@Component
public class JwtUtil {

    private final String SECRET_KEY = "claveSuperSecretaSeguraQueDebeTenerAlMenos32Caracteres"; // mínimo 32 caracteres

    // 🔐 Genera la llave de firma compatible con jjwt 0.11.x
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // 🔐 Generar token JWT con tiempo de expiración de 1 hora
    public String generarToken(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("nombres", usuario.getEmpleado().getNombres());
        claims.put("apellidoPaterno", usuario.getEmpleado().getApellidoPaterno());
        claims.put("rol", usuario.getRol().getNombreRol());
        claims.put("idRol", usuario.getRol().getIdRol());
        claims.put("permisos", usuario.getRol().getRolPermisos()
                .stream()
                .map(rp -> rp.getPermiso().getNombrePermiso())
                .collect(Collectors.toList()));


        return Jwts.builder()
                .setClaims(claims)
                .setSubject(usuario.getNombreUsuario())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hora
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ Validar token con UserDetails
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // 🔍 Verifica si el token ya expiró
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 📆 Extrae la fecha de expiración desde los claims
    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    // 🎯 Extrae el username desde el token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // 📦 Extrae los claims usando el parserBuilder (necesario con jjwt 0.11+)
    public Claims extractClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}