package ru.test.ping.services;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import ru.test.ping.entities.dtos.ExecutionDto;

import java.util.Map;

/**
 * Сервис для работы с сущностями домена.
 */
public interface ExecutionService {

    /**
     * Получение {@link Page< ExecutionDto >} по номеру страницы.
     *
     * @param requestParams параметры поиска.
     * @return {@link Page< ExecutionDto >}
     */
    Page<ExecutionDto> findExecutions(Map<String, String> requestParams);

    /**
     * Исполнение команды ping с сохранением результата.
     *
     * @param address адрес исполнения команды ping.
     * @return {@link ExecutionDto}
     */
    ExecutionDto executeCommand(@NonNull String address);
}
