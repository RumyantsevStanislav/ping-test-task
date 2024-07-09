package ru.test.ping.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.test.ping.entities.Execution;
import ru.test.ping.repositories.ExecutionRepository;
import ru.test.ping.services.CommandExecutor;

import java.util.TimerTask;

import static ru.test.ping.entities.Execution.ExecutionState.COMPLETED;
import static ru.test.ping.entities.Execution.ExecutionState.IN_PROGRESS;

/**
 * Исполнитель команды в заданное время.
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class TimerExecutor extends TimerTask {
    /**
     * Репозиторий для доступа к записям о доменах.
     */
    private final ExecutionRepository executionRepository;

    private final CommandExecutor commandExecutor;

    private final Execution plannedExecution;

    @Override
    public void run() {
        plannedExecution.setExecutionState(IN_PROGRESS);
        executionRepository.save(plannedExecution);
        String result = commandExecutor.executeCommand(plannedExecution.getAddress());
        plannedExecution.setExecutionState(COMPLETED);
        plannedExecution.setExecutionResult(result);
        executionRepository.save(plannedExecution);
    }
}
