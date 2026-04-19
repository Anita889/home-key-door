package com.example.homekeydoor.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/home-user")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_HOME_USER')")
public class HomeUserController {

    @PostMapping("/take-key/{keyId}")
    public String takeKey(@PathVariable Long keyId) {
        return "Key taken: " + keyId;
    }

    @PostMapping("/return-key/{keyId}")
    public String returnKey(@PathVariable Long keyId) {
        return "Key returned: " + keyId;
    }

    @PostMapping("/report-lost/{keyId}")
    public String reportLostKey(@PathVariable Long keyId) {
        return "Lost key reported. Owner notified.";
    }
}
