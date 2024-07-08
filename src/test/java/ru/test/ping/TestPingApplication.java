package ru.test.ping;

import org.springframework.boot.SpringApplication;

public class TestPingApplication {

    public static void main(String[] args) {
        SpringApplication.from(PingApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
