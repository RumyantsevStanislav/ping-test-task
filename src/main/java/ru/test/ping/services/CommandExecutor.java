package ru.test.ping.services;

/**
 * Исполнитель консольных команд.
 */
public interface CommandExecutor {
    /**
     * Исполнить консольную команду.
     *
     * @param command исполняемая команда.
     * @return результат исполнения.
     */
    String executeCommand(String command);
}
