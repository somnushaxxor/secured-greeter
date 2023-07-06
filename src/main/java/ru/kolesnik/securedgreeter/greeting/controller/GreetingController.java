package ru.kolesnik.securedgreeter.greeting.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/greeting")
    public String userGreeting() {
        return "Hello, user!";
    }

    @GetMapping("/admin/greeting")
    public String adminGreeting() {
        return "Hello, admin!";
    }

}
