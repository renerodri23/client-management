package com.renerodriguez.core_client_management_system.adapters.in.rest.controller;

import com.renerodriguez.core_client_management_system.adapters.in.rest.dto.CreateAddressRequest;
import com.renerodriguez.core_client_management_system.adapters.in.rest.dto.CreateDocumentRequest;
import com.renerodriguez.core_client_management_system.adapters.in.rest.dto.UserResponse;
import com.renerodriguez.core_client_management_system.adapters.in.rest.mapper.UserRestMapper;
import com.renerodriguez.core_client_management_system.application.port.in.UserUseCase;
import com.renerodriguez.core_client_management_system.application.port.in.UserUpdateUseCase;
import com.renerodriguez.core_client_management_system.application.port.in.command.DeleteAddressCommand;
import com.renerodriguez.core_client_management_system.application.port.in.command.DeleteDocumentCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserUpdateController {

    private final UserUpdateUseCase userUpdateUseCase;
    private final UserUseCase userUseCase;
    private final UserRestMapper userRestMapper;

    @DeleteMapping("/{userUuid}/addresses/{addressUuid}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwner(authentication, #userUuid)")
    public ResponseEntity<Void> deleteAddress(
            @PathVariable UUID userUuid,
            @PathVariable UUID addressUuid) {

        userUpdateUseCase.deleteAddress(new DeleteAddressCommand(userUuid, addressUuid));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userUuid}/documents/{documentUuid}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwner(authentication, #userUuid)")
    public ResponseEntity<Void> deleteDocument(
            @PathVariable UUID userUuid,
            @PathVariable UUID documentUuid) {

        userUpdateUseCase.deleteDocument(new DeleteDocumentCommand(userUuid, documentUuid));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/addresses")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwner(authentication, #id)")
    public ResponseEntity<UserResponse> addAddress(
            @PathVariable UUID id,
            @Valid @RequestBody CreateAddressRequest request) {

        log.info("[UserUpdateController] - Agregando dirección al usuario ID: {}", id);

        var command = userRestMapper.toAddAddressCommand(id, request);
        userUpdateUseCase.addAddress(command);

        return userUseCase.findById(id)
                .map(userRestMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/documents")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwner(authentication, #id)")
    public ResponseEntity<UserResponse> addDocument(
            @PathVariable UUID id,
            @Valid @RequestBody CreateDocumentRequest request) {

        log.info("[UserUpdateController] - Agregando documento al usuario ID: {}", id);

        var command = userRestMapper.toAddDocumentCommand(id, request);
        userUpdateUseCase.addDocument(command);

        return userUseCase.findById(id)
                .map(userRestMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}