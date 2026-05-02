package com.renerodriguez.core_client_management_system.application.port.in;

import com.renerodriguez.core_client_management_system.application.port.in.command.AddAddressCommand;
import com.renerodriguez.core_client_management_system.application.port.in.command.AddDocumentCommand;
import com.renerodriguez.core_client_management_system.application.port.in.command.DeleteAddressCommand;
import com.renerodriguez.core_client_management_system.application.port.in.command.DeleteDocumentCommand;

import java.util.UUID;
/**
 * Puerto de entrada que define las operaciones para la actualización de información de usuarios.
 * <p>
 * Esta interfaz expone los casos de uso necesarios para modificar los datos suplementarios
 * de un usuario, tales como la gestión de sus direcciones físicas y documentos de identidad.
 * </p>
 * * @author Rene Rodriguez
 * @version 1.0
 */
public interface UserUpdateUseCase {
    void deleteAddress(DeleteAddressCommand command);
    void deleteDocument(DeleteDocumentCommand command);
    void addAddress(AddAddressCommand command);
    void addDocument(AddDocumentCommand command);
}