package ru.test.ping.utils;

import lombok.experimental.UtilityClass;

/**
 * Утилитный класс для хранения констант.
 */
@UtilityClass
public class Consts {
    /**
     * Количество записей на странице списка доменов.
     */
    public static int DOMAIN_LIST_PAGE_SIZE = 5;

    public static final String NOT_EXECUTED = "Не выполнено";

    /**
     * Исполняемые команды.
     */
    public static class Commands {
        public static String PING = "ping";
    }
}
