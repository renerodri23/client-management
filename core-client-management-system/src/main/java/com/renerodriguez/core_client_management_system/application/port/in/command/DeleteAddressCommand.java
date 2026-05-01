package com.renerodriguez.core_client_management_system.application.port.in.command;

import java.util.UUID;

public record DeleteAddressCommand(UUID userUuid, UUID addressUuid) {}