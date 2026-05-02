package com.renerodriguez.core_client_management_system.adapters.in.rest.controller;

import com.renerodriguez.core_client_management_system.adapters.in.rest.dto.*;
import com.renerodriguez.core_client_management_system.adapters.in.rest.mapper.UserRestMapper;
import com.renerodriguez.core_client_management_system.application.port.in.UserUseCase;
import com.renerodriguez.core_client_management_system.application.port.in.command.CreateUserCommand;
import com.renerodriguez.core_client_management_system.domain.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
/**

 Controlador REST encargado de la gestión de usuarios.
 Expone operaciones para:
 Crear usuarios
 Actualizar usuarios
 Consultar usuarios por ID y email
 Listar todos los usuarios
 Verificar existencia de email
 Generar reporte de usuarios
 Activar usuarios
 Eliminar (desactivar) usuarios
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {
    private final UserRestMapper userRestMapper;
    private final UserUseCase userUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest request){
        log.info("[UserController] - Creando usuario con email: {}", request.email());

        CreateUserCommand command = userRestMapper.toCreateCommand(request);

        User user = userUseCase.createUser(command);

        log.info("[UserController] - Usuario creado con ID: {}", user.uuid());
        return userRestMapper.toResponse(user);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwner(authentication, #id)")
    public ResponseEntity<UserResponse> updateUser(@PathVariable UUID id, @Valid @RequestBody UpdateUserRequest request){
        log.info("[UserController] - Peticion para actualizar usuario ID: {}", id);

        var command = userRestMapper.toUpdateCommand(id,request);
        var user = userUseCase.updateUser(command);

        return ResponseEntity.ok(userRestMapper.toResponse(user));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwner(authentication, #id)")
    public ResponseEntity<UserResponse> findUserById(@PathVariable UUID id){
        log.info("[UserController] - Buscando usuario por ID: {}", id);
        return userUseCase.findById(id)
                .map(userRestMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('ADMIN') or #email == authentication.name")
    public ResponseEntity<UserResponse> findUserByEmail(@PathVariable String email) {
        log.info("[UserController] - Buscando usuario por email: {}", email);
        return userUseCase.findByEmail(email)
                .map(userRestMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll(){
        log.info("[UserController] - Buscando todos los usuarios");
        var users = userUseCase.findAll().stream()
                .map(userRestMapper::toResponse)
                .toList();
        return ResponseEntity.ok(users);
    }
    @PostMapping("/check-email")
    public ResponseEntity<ExistsResponse> checkEmail(@Valid @RequestBody ExistByEmailRequest request) {
        log.info("[UserController] - Verificando si existe email: {}", request.email());
        boolean exists = userUseCase.existsByEmail(request.email());
        return ResponseEntity.ok(new ExistsResponse(exists));
    }

    @GetMapping("/reporte")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> downloadReport() {
        byte[] report = userUseCase.generateUserReport();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_clientes.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(report);
    }

    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activateUser(@PathVariable UUID id) {
        log.info("[UserController] - Petición para activar usuario ID: {}", id);
        userUseCase.activateUser(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwner(authentication, #id)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID id) {
        log.info("[UserController] - Petición para eliminar (desactivar) usuario ID: {}", id);
        userUseCase.deleteUser(id);
    }
}
