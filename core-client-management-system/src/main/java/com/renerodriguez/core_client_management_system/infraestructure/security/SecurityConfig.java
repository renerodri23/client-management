package com.renerodriguez.core_client_management_system.infraestructure.security;

import com.renerodriguez.core_client_management_system.adapters.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.authentication.AuthenticationProvider;

import java.util.Arrays;
import java.util.List;
/**
 * Clase de configuración principal para la seguridad del sistema.
 * <p>
 * Esta clase define la arquitectura de seguridad basada en Spring Security, estableciendo
 * políticas de acceso, gestión de sesiones sin estado (Stateless), configuración de CORS,
 * y la integración del filtro de autenticación JWT.
 * </p>
 * * @author Rene Rodriguez
 * @version 1.0
 */

/**
 * Configura la cadena de filtros de seguridad (Security Filter Chain).
 * <p>
 * Define las reglas de autorización para las peticiones HTTP:
 * <ul>
 * <li>Deshabilita CSRF (innecesario en APIs stateless).</li>
 * <li>Establece la política de sesión como STATELESS.</li>
 * <li>Configura un punto de entrada de autenticación personalizado para errores 401.</li>
 * <li>Permite el acceso público a endpoints de autenticación, registro y documentación Swagger.</li>
 * <li>Restringe los endpoints de administración al rol ADMIN.</li>
 * <li>Integra el {@link JwtAuthenticationFilter} antes del filtro de usuario/contraseña.</li>
 * </ul>
 * </p>
 * * @param http Objeto de configuración de seguridad web.
 * @return La cadena de filtros configurada.
 * @throws Exception Si ocurre un error en la configuración.
 */

/**
 * Define la política de Intercambio de Recursos de Origen Cruzado (CORS).
 * <p>
 * Configura los orígenes permitidos desde las propiedades del sistema, así como los
 * métodos HTTP (GET, POST, etc.) y las cabeceras autorizadas para permitir la
 * comunicación con el frontend.
 * </p>
 * * @return La fuente de configuración CORS.
 */

/**
 * Define el codificador de contraseñas utilizando el algoritmo BCrypt.
 * * @return Una instancia de {@link BCryptPasswordEncoder}.
 */

/**
 * Configura el proveedor de autenticación de la aplicación.
 * <p>
 * Utiliza {@link DaoAuthenticationProvider} vinculando el servicio de detalles de
 * usuario y el codificador de contraseñas para validar las credenciales.
 * </p>
 * * @return El proveedor de autenticación configurado.
 */

/**
 * Expone el gestor de autenticación estándar de Spring Security.
 * * @param config Configuración de autenticación de Spring.
 * @return El {@link AuthenticationManager} de la aplicación.
 * @throws Exception Si no se puede recuperar el gestor.
 */
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    @Value("${app.cors.allowed-origins}")
    private String allowedOrigins;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\": \"No autorizado\", \"message\": \"" + authException.getMessage() + "\"}");

                        })
                )
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()

                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(allowedOrigins));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH","OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider =
                new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setHideUserNotFoundExceptions(false);

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
