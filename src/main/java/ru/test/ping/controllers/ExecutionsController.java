package ru.test.ping.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.test.ping.entities.Execution;
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
@Controller
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
    public String findAll(@RequestParam Map<String, String> requestParams, Model model) {
        Page<ExecutionDto> executions = executionService.findExecutions(requestParams);
        model.addAttribute("executions", executions);
        model.addAttribute("states", Execution.ExecutionState.values());
        return "executions_page";
    }

    @GetMapping("/{id}")
    public String findResultById(@PathVariable @NonNull UUID id, Model model) {
        ExecutionResultDto executionResult = executionService.findResultById(id);
        model.addAttribute("executionResult", executionResult);
        return "details_page";
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable @NonNull UUID id) {
        executionService.deleteById(id);
        return "redirect:/api/v1/executions";
    }

    @GetMapping(EXECUTE)
    public String ping(@RequestParam String address, @RequestParam(required = false) OffsetDateTime startTime) {
        executionService.executeCommand(address, startTime);
        return "redirect:/api/v1/executions";
    }
}
