package com.renerodriguez.core_client_management_system.application.service;

import com.renerodriguez.core_client_management_system.adapters.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
/**
 * Servicio de aplicación encargado de gestionar los procesos de autenticación y seguridad.
 * <p>
 * Esta clase actúa como orquestador para validar las credenciales de los usuarios
 * y emitir tokens de acceso (JWT) que permiten la comunicación segura en peticiones posteriores.
 * </p>
 *
 * @author Rene Rodriguez
 * @version 1.0
 */

/**
 * Realiza el proceso de autenticación de un usuario y genera un token de acceso.
 * <p>
 * El flujo de ejecución comprende:
 * <ol>
 * <li>Intento de autenticación mediante el {@code AuthenticationManager}.</li>
 * <li>Carga de los detalles del usuario a partir del correo electrónico.</li>
 * <li>Generación de un token JWT firmado si las credenciales son válidas.</li>
 * </ol>
 * </p>
 *
 * *@param email    Correo electrónico del usuario que intenta acceder.
 * *@param password Contraseña en texto plano para validar contra el hash almacenado.
 * *@return Un {@code String} que contiene el token JWT generado para la sesión.
 * *@throws org.springframework.security.core.AuthenticationException si las credenciales son inválidas.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    public String login(String email, String password) {
        log.info("[AuthService] Intento de login para email: {}", email);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        var userDetails = userDetailsService.loadUserByUsername(email);

        String token = jwtUtils.generateToken(userDetails);
        log.info("[AuthService] Login exitoso para email: {}", email);
        return token;
    }
}