package com.renerodriguez.core_client_management_system.application.port.in.command;

import java.util.UUID;

public record DeleteDocumentCommand(UUID userUuid, UUID documentUuid) {}
