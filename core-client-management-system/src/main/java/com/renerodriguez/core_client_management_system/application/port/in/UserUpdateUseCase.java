package com.renerodriguez.core_client_management_system.application.port.in;

import com.renerodriguez.core_client_management_system.application.port.in.command.AddAddressCommand;
import com.renerodriguez.core_client_management_system.application.port.in.command.AddDocumentCommand;
import com.renerodriguez.core_client_management_system.application.port.in.command.DeleteAddressCommand;
import com.renerodriguez.core_client_management_system.application.port.in.command.DeleteDocumentCommand;

import java.util.UUID;

public interface UserUpdateUseCase {
    void deleteAddress(DeleteAddressCommand command);
    void deleteDocument(DeleteDocumentCommand command);
    void addAddress(AddAddressCommand command);
    void addDocument(AddDocumentCommand command);
}