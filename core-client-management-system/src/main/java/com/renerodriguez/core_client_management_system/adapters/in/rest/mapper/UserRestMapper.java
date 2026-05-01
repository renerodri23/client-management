package com.renerodriguez.core_client_management_system.adapters.in.rest.mapper;

import com.renerodriguez.core_client_management_system.adapters.in.rest.dto.*;
import com.renerodriguez.core_client_management_system.application.port.in.command.*;
import com.renerodriguez.core_client_management_system.domain.User;
import com.renerodriguez.core_client_management_system.domain.enums.TipoDireccion;
import com.renerodriguez.core_client_management_system.domain.enums.TipoDocumentoIdentidad;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UserRestMapper {

    public CreateUserCommand toCreateCommand(CreateUserRequest request){
        return new CreateUserCommand(
                request.nombre(),
                request.apellido(),
                request.email(),
                request.password(),
                mapDireccionesToCommand(request.direcciones()),
                mapDocumentosToCommand(request.documentos()),
                request.role()
        );
    }

    public UpdateUserCommand toUpdateCommand(UUID id, UpdateUserRequest request){
        return new UpdateUserCommand(
                id,
                request.nombre(),
                request.apellido(),
                request.email(),
                mapDireccionesToCommand(request.direcciones()),
                mapDocumentosToCommand(request.documentos()),
                request.role()
        );
    }

    public UserResponse toResponse(User user){
        return new UserResponse(
                user.uuid(),
                user.nombre(),
                user.apellido(),
                user.email(),
                user.direcciones().stream()
                        .map(d-> new DireccionResponse(d.uuidDir(),d.calle(), d.ciudad(),d.departamento(), d.tipo().name()))
                        .toList(),
                user.documentosIdentidad().stream()
                        .map(doc -> new DocumentoResponse(doc.uuidDoc(),doc.tipo().name(), doc.valor()))
                        .toList(),
                user.role(),
                user.active()
        );
    }
    private List<DireccionesCommand> mapDireccionesToCommand(List<DireccionRequest> requests) {
        if (requests == null) return null;
        return requests.stream()
                .map(d -> new DireccionesCommand(
                        d.calle(), d.ciudad(), d.departamento(), TipoDireccion.valueOf(d.tipo().toUpperCase())))
                .toList();
    }

    private List<DocumentoCommand> mapDocumentosToCommand(List<DocumentoRequest> requests) {
        if (requests == null) return null;
        return requests.stream()
                .map(d -> new DocumentoCommand(
                        TipoDocumentoIdentidad.valueOf(d.tipo().toUpperCase()), d.valor()))
                .toList();
    }

    public AddAddressCommand toAddAddressCommand(UUID id, CreateAddressRequest request) {
        return new AddAddressCommand(
                id,
                request.calle(),
                request.ciudad(),
                request.departamento(),
                request.tipo()
        );
    }

    public AddDocumentCommand toAddDocumentCommand(UUID id, CreateDocumentRequest request) {
        return new AddDocumentCommand(
                id,
                request.tipo(),
                request.valor()
        );
    }

}
