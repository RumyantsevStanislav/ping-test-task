package ru.test.ping.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.test.ping.entities.dtos.ExecutionDto;
import ru.test.ping.services.ExecutionService;

/**
 * Контроллер для работы с запусками команды ping.
 */
@RestController
@RequestMapping("/api/v1/executions")
@RequiredArgsConstructor
public class ExecutionsController {
    /**
     * Сервис для работы с командами ping.
     */
    private final ExecutionService executionService;

    /**
     * Поиск записей о запусках команды ping с пагинацией.
     *
     * @param pageNumber номер страницы.
     * @return информация о запусках на заданной странице.
     */
    @GetMapping
    public Page<ExecutionDto> findAll(@RequestParam(name = "page_number", required = false, defaultValue = "0") int pageNumber) {
        return executionService.findExecutions(pageNumber);
    }

}
