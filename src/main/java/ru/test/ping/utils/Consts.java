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
        public static final String PING = "ping";
    }

    public static class RequestParams {
        public static final String PAGE_NUMBER = "page_number";
        public static final String ADDRESS = "address";
        public static final String FROM = "from";
        public static final String TO = "to";
        public static final String STATE = "state";
    }

    public static class RequestPaths {
        public static final String ROOT = "/api/v1/executions";
        public static final String EXECUTE = "/execute";
    }

}
