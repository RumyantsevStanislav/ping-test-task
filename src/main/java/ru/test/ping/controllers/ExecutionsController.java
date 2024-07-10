package ru.test.ping.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.test.ping.entities.dtos.ExecutionDto;
import ru.test.ping.entities.dtos.ExecutionResultDto;
import ru.test.ping.services.ExecutionService;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

import static ru.test.ping.utils.Consts.RequestPaths.EXECUTE;
import static ru.test.ping.utils.Consts.RequestPaths.ROOT;

/**
 * Контроллер для работы с запусками команды ping.
 */
@RestController
@RequestMapping(ROOT)
@RequiredArgsConstructor
public class ExecutionsController {
    /**
     * Сервис для работы с командами ping.
     */
    private final ExecutionService executionService;

    /**
     * Поиск записей о запусках команды ping с пагинацией.
     *
     * @param requestParams параметры поиска.
     * @return информация о запусках на заданной странице.
     */
    @GetMapping
    public Page<ExecutionDto> findAll(@RequestParam Map<String, String> requestParams, Model model) {
        Page<ExecutionDto> executions = executionService.findExecutions(requestParams);
        model.addAttribute("executions", executions);
        return executions;
    }

    @GetMapping("/{id}")
    public ExecutionResultDto findResultById(@PathVariable @NonNull UUID id, Model model) {
        ExecutionResultDto executionResult = executionService.findResultById(id);
        model.addAttribute("executionResult", executionResult);
        return executionResult;
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable @NonNull UUID id, Model model) {
        executionService.deleteById(id);
        return "executions_page";
    }

    @GetMapping(EXECUTE)
    public String ping(@RequestParam String address, @RequestParam(required = false) OffsetDateTime startTime) {
        executionService.executeCommand(address, startTime);
        return "executions_page";
    }
}
