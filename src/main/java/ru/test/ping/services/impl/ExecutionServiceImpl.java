package ru.test.ping.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.test.ping.entities.Execution;
import ru.test.ping.entities.dtos.ExecutionDto;
import ru.test.ping.entities.dtos.ExecutionResultDto;
import ru.test.ping.repositories.ExecutionRepository;
import ru.test.ping.services.CommandExecutor;
import ru.test.ping.services.ExecutionService;
import ru.test.ping.utils.ExecutionsFilter;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

import static ru.test.ping.entities.Execution.ExecutionState.COMPLETED;
import static ru.test.ping.entities.Execution.ExecutionState.IN_PROGRESS;
import static ru.test.ping.mappers.ExecutionMapper.EXECUTION_MAPPER;
import static ru.test.ping.utils.Consts.DOMAIN_LIST_PAGE_SIZE;
import static ru.test.ping.utils.Consts.RequestParams.PAGE_NUMBER;

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
    public Page<ExecutionDto> findExecutions(Map<String, String> requestParams) {
        final int pageNumber = Integer.parseInt(requestParams.getOrDefault(PAGE_NUMBER, "0"));
        Page<Execution> domainPage = executionRepository.findAll(new ExecutionsFilter(requestParams).getExecutionSpecification(), PageRequest.of(pageNumber, DOMAIN_LIST_PAGE_SIZE));
        return domainPage.map(EXECUTION_MAPPER::toDto);
    }

    @Override
    public ExecutionResultDto findResultById(UUID id) {
        return EXECUTION_MAPPER.toResultDto(executionRepository.findById(id).orElseThrow(() -> new RuntimeException("")));
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
