package ru.test.ping.services;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import ru.test.ping.entities.dtos.ExecutionDto;

/**
 * Сервис для работы с сущностями домена.
 */
public interface ExecutionService {

    /**
     * Получение {@link Page< ExecutionDto >} по номеру страницы.
     *
     * @param pageNumber номер страницы.
     * @return {@link Page< ExecutionDto >}
     */
    Page<ExecutionDto> findExecutions(int pageNumber);

    /**
     * Исполнение команды ping с сохранением результата.
     *
     * @param address адрес исполнения команды ping.
     * @return {@link ExecutionDto}
     */
    ExecutionDto executeCommand(@NonNull String address);
}
