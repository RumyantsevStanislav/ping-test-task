package ru.test.ping.services.impl;

import jakarta.annotation.Nullable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.test.ping.configuraions.TimerExecutorConfig;
import ru.test.ping.entities.Execution;
import ru.test.ping.entities.dtos.ExecutionDto;
import ru.test.ping.entities.dtos.ExecutionResultDto;
import ru.test.ping.repositories.ExecutionRepository;
import ru.test.ping.services.CommandExecutor;
import ru.test.ping.services.ExecutionService;
import ru.test.ping.utils.ExecutionsFilter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static java.util.Objects.isNull;
import static ru.test.ping.entities.Execution.ExecutionState.PLANNED;
import static ru.test.ping.mappers.ExecutionMapper.EXECUTION_MAPPER;
import static ru.test.ping.utils.Consts.DOMAIN_LIST_PAGE_SIZE;
import static ru.test.ping.utils.Consts.MOSCOW_TIME_ZONE;
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
    /**
     * Конфигурация бинов, участвующих в исполнении задач по-расписанию.
     */
    private final TimerExecutorConfig timerExecutorConfig;

    @Override
    public Page<ExecutionDto> findExecutions(Map<String, String> requestParams) {
        final int pageNumber = Integer.parseInt(requestParams.getOrDefault(PAGE_NUMBER, "1")) - 1;
        Page<Execution> domainPage = executionRepository.findAll(new ExecutionsFilter(requestParams).getExecutionSpecification(), PageRequest.of(pageNumber, DOMAIN_LIST_PAGE_SIZE));
        return domainPage.map(EXECUTION_MAPPER::toDto);
    }

    @Override
    public ExecutionResultDto findResultById(UUID id) {
        return EXECUTION_MAPPER.toResultDto(executionRepository.findById(id).orElseThrow(() -> new RuntimeException("")));
    }

    @Override
    public void deleteById(UUID id) {
        executionRepository.deleteById(id);
    }


    @Override
    public void executeCommand(@NonNull String address, @Nullable LocalDateTime startTime) {
        OffsetDateTime offsetStart = isNull(startTime)
                ? OffsetDateTime.now()
                : OffsetDateTime.of(startTime, ZoneId.of(MOSCOW_TIME_ZONE).getRules().getOffset(startTime));
        Execution plannedExecution = executionRepository.save(createExecution(address, offsetStart));
        timerExecutorConfig.getTimerInstance().schedule(
                new TimerExecutor(executionRepository, commandExecutor, plannedExecution),
                convertStartTime(offsetStart));
    }

    /**
     * Преобразование времени старта исполнения команды в {@link Date}
     *
     * @param startTime время старта команды.
     * @return {@link Date}
     */
    private Date convertStartTime(OffsetDateTime startTime) {
        if (isNull(startTime)) {
            return new Date();
        } else {
            return Date.from(startTime.toInstant());
        }
    }

    /**
     * Создание сущности о запуске команды ping.
     *
     * @return сущность о запуске команды ping.
     */
    private Execution createExecution(String address, OffsetDateTime offsetStart) {
        Execution execution = new Execution();
        execution.setAddress(address);
        execution.setExecutedAt(offsetStart);
        execution.setExecutionState(PLANNED);
        return execution;
    }

}
