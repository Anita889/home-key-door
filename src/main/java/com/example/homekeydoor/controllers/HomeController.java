package com.example.homekeydoor.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/homes")
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/{id}")
    public String getHome(@PathVariable Long id) {
        return "Home details: " + id;
    }

    @GetMapping
    public String getAllHomes() {
        return "All homes";
    }
}