package ru.test.ping.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Сущность, хранящая информацию о запусках команды ping.
 */
@Entity
@Getter
@Setter
@Table(name = "executions")
public class Execution {
    /**
     * Идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    /**
     * Адрес запроса.
     */
    private String address;
    /**
     * Состояние запуска.
     */
    @Column(name = "execution_state")
    @Enumerated(EnumType.STRING)
    private ExecutionState executionState;
    /**
     * Время запуска.
     */
    @Column(name = "executed_at")
    private OffsetDateTime executedAt;
    /**
     * Результат выполнения.
     */
    @Column(name = "execution_result")
    private String executionResult;

    /**
     * Состояние запуска.
     */
    @Getter
    public enum ExecutionState {
        PLANNED,
        IN_PROGRESS,
        COMPLETED;
    }
}
