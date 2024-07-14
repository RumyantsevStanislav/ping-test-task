package ru.test.ping.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {
    @GetMapping("/")
    public String homepage() {
        return "index";
    }

    @GetMapping("/executions")
    public String executions() {
        return "executions_page";
    }
}
