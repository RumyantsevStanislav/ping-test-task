package ru.test.ping.entities.dtos;

import java.time.OffsetDateTime;

/**
 * Объект для передачи на фронт данных о доменах.
 */
public record ExecutionDto(String address, String executionState, OffsetDateTime executedAt) {
}
