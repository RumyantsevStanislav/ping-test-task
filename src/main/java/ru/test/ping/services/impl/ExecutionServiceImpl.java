package ru.test.ping.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.test.ping.entities.Execution;
import ru.test.ping.entities.dtos.ExecutionDto;
import ru.test.ping.repositories.ExecutionRepository;
import ru.test.ping.services.CommandExecutor;
import ru.test.ping.services.ExecutionService;

import java.time.OffsetDateTime;

import static ru.test.ping.entities.Execution.ExecutionState.COMPLETED;
import static ru.test.ping.entities.Execution.ExecutionState.IN_PROGRESS;
import static ru.test.ping.mappers.ExecutionMapper.EXECUTION_MAPPER;
import static ru.test.ping.utils.Consts.DOMAIN_LIST_PAGE_SIZE;

/**
 * Сервис для работы с сущностями домена.
 */
@Service
@RequiredArgsConstructor
public class ExecutionServiceImpl implements ExecutionService {
    /**
     * Репозиторий для доступа к записям о доменах.
     */
    private final ExecutionRepository executionRepository;
    /**
     * Исполнитель команды ping.
     */
    private final CommandExecutor commandExecutor;

    @Override
    public Page<ExecutionDto> findExecutions(int pageNumber) {
        Page<Execution> domainPage = executionRepository.findAll(PageRequest.of(pageNumber, DOMAIN_LIST_PAGE_SIZE));
        return domainPage.map(EXECUTION_MAPPER::toDto);
    }

    @Override
    public ExecutionDto executeCommand(@NonNull String address) {
        Execution inProgressExecution = createExecution(address);
        Execution savedExecution = executionRepository.save(inProgressExecution);
        String result = commandExecutor.executeCommand(address);
        savedExecution.setExecutionResult(result);
        savedExecution.setExecutionState(COMPLETED);
        return EXECUTION_MAPPER.toDto(executionRepository.save(savedExecution));
    }

    /**
     * Создание сущности о запуске команды ping.
     *
     * @return сущность о запуске команды ping.
     */
    private Execution createExecution(String address) {
        Execution execution = new Execution();
        execution.setAddress(address);
        execution.setExecutedAt(OffsetDateTime.now());
        execution.setExecutionState(IN_PROGRESS);
        return execution;
    }

}
