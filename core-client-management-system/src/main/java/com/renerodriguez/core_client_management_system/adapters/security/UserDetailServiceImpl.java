package com.renerodriguez.core_client_management_system.adapters.security;

import com.renerodriguez.core_client_management_system.application.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * Implementación de {@link UserDetailsService} para la carga de usuarios
 * desde el repositorio de usuarios en el contexto de seguridad.
 * <p>
 * Esta clase se encarga de recuperar la información de un usuario a partir
 * de su correo electrónico y adaptarla al modelo de {@link UserDetails}
 * requerido por Spring Security.
 */
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public boolean equals(Object obj){
        return super.equals(obj);
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)

                .map(user ->
                        User.builder()
                        .username(user.email())
                        .password(user.passwordHash())
                        .roles(user.role().name())
                        .disabled(!user.active()).accountLocked(!user.active())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email:" + email));
    }
}
