package com.example.homekeydoor.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/owner")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_HOME_OWNER')")
public class HomeOwnerController {

    @PostMapping("/home")
    public String createHome() {
        return "Home created";
    }

    @GetMapping("/homes")
    public String getMyHomes() {
        return "Owner homes list";
    }

    @PostMapping("/give-admin-access/{adminId}")
    public String giveAdminAccess(@PathVariable Long adminId) {
        return "Access given to admin: " + adminId;
    }

    @GetMapping("/keys")
    public String seeWhoHasKeys() {
        return "List of key holders";
    }

    @PostMapping("/keys")
    public String createKey() {
        return "Key created";
    }
}