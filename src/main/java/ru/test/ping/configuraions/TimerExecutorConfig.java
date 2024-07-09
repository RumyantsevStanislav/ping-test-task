package ru.test.ping.configuraions;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Timer;

/**
 * Конфигурация бинов, участвующих в исполнении задач по-расписанию.
 */
@Configuration
public class TimerExecutorConfig {

    /**
     * Бин задачи по-расписанию.
     */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Timer getTimerInstance() {
        return new Timer();
    }
}
