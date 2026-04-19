package com.example.homekeydoor.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/keys")
@RequiredArgsConstructor
public class KeyController {

    @GetMapping("/{id}")
    public String getKey(@PathVariable Long id) {
        return "Key details: " + id;
    }

    @GetMapping
    public String getAllKeys() {
        return "All keys";
    }
}