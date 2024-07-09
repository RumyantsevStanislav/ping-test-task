package ru.test.ping.entities.dtos;

import java.util.UUID;

/**
 * Объект для передачи на фронт данных о результате исполнения команды.
 */
public record ExecutionResultDto(UUID id, String executionResult) {
}
