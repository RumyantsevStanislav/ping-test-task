package ru.test.ping.services.impl;

import lombok.NonNull;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import ru.test.ping.services.CommandExecutor;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import static ru.test.ping.utils.Consts.Commands.PING;
import static ru.test.ping.utils.Consts.NOT_EXECUTED;

/**
 * Сервис, исполняющий консольные команды.
 */
@Service
public class CommandExecutorImpl implements CommandExecutor {

    @Override
    public String executeCommand(@NonNull String ipOrDomain) {
        try {
            return interpretResult(Runtime.getRuntime().exec(String.format("%s -c 5 %s", PING, ipOrDomain)));
        } catch (IOException ioException) {
            return NOT_EXECUTED;
        }
    }

    /**
     * Интерпретация полученного результата.
     *
     * @param process результат исполнения команды.
     * @return вывод консоли при исполнении команды.
     * @throws IOException в случае ошибки чтения.
     */
    private String interpretResult(Process process) throws IOException {
        StringWriter writer = new StringWriter();
        IOUtils.copy(process.getInputStream(), writer, StandardCharsets.UTF_8);
        return writer.toString();
    }
}
