package ru.kolesnik.springsecuritydemo.greeting.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting() {
        return "Hello there!";
    }

    @GetMapping("/admin/greeting")
    public String adminGreeting() {
        return "Hello admin!";
    }

}
