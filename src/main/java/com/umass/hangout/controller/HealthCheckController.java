package com.umass.hangout.controller;

@RestController
public class HealthCheckController {
    @GetMapping("/")
    public String home() {
        return "Spring Boot application is running!";
    }
}
