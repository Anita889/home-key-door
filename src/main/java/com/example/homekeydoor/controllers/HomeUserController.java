package com.example.homekeydoor.controllers;

import com.example.homekeydoor.dataservices.HomeUserDataService;
import com.example.homekeydoor.dataservices.KeyDataService;
import com.example.homekeydoor.dtos.KeyDTO;
import com.example.homekeydoor.entities.HomeUser;
import com.example.homekeydoor.entities.Key;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/home-user")
@RequiredArgsConstructor
@PreAuthorize("hasRole('HOME_USER')")
public class HomeUserController {

    private final HomeUserDataService homeUserDataService;
    private final KeyDataService keyDataService;

    @GetMapping("/{homeUserId}/keys")
    public List<KeyDTO> getMyKeys(@PathVariable Long homeUserId) {
        homeUserDataService.getHomeUserById(homeUserId);
        return keyDataService.getKeysByHomeUserId(homeUserId).stream()
                .map(this::toDTO)
                .toList();
    }

    @PostMapping("/{homeUserId}/keys/{keyId}/take")
    public KeyDTO takeKey(@PathVariable Long homeUserId, @PathVariable Long keyId) {
        HomeUser homeUser = homeUserDataService.getHomeUserById(homeUserId);
        return toDTO(keyDataService.takeKey(keyId, homeUser));
    }

    @PostMapping("/{homeUserId}/keys/{keyId}/return")
    public KeyDTO returnKey(@PathVariable Long homeUserId, @PathVariable Long keyId) {
        HomeUser homeUser = homeUserDataService.getHomeUserById(homeUserId);
        return toDTO(keyDataService.returnKey(keyId, homeUser));
    }

    @PostMapping("/{homeUserId}/keys/{keyId}/report-lost")
    public KeyDTO reportLostKey(@PathVariable Long homeUserId, @PathVariable Long keyId) {
        HomeUser homeUser = homeUserDataService.getHomeUserById(homeUserId);
        return toDTO(keyDataService.reportLost(keyId, homeUser));
    }

    private KeyDTO toDTO(Key key) {
        KeyDTO dto = new KeyDTO();
        dto.setId(key.getId());
        dto.setCode(key.getCode());
        dto.setStatus(key.getStatus());
        dto.setHomeId(key.getHome().getId());
        dto.setHomeName(key.getHome().getName());
        dto.setOwnerId(key.getHome().getOwner().getId());
        dto.setOwnerName(key.getHome().getOwner().getUser().getFullName());
        if (key.getHomeUser() != null) {
            dto.setHomeUserId(key.getHomeUser().getId());
            dto.setHomeUserName(key.getHomeUser().getUser().getFullName());
        }
        return dto;
    }
}
