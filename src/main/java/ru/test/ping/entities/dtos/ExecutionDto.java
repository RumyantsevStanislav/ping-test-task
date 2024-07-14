package ru.test.ping.entities.dtos;

import java.util.UUID;

/**
 * Объект для передачи на фронт данных о запросах.
 */
public record ExecutionDto(UUID id, String address, String executionState, String executedAt) {
}
