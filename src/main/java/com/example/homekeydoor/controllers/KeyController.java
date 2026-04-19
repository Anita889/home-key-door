package com.example.homekeydoor.controllers;

import com.example.homekeydoor.dataservices.KeyDataService;
import com.example.homekeydoor.dtos.KeyDTO;
import com.example.homekeydoor.entities.Key;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/keys")
@RequiredArgsConstructor
public class KeyController {

    private final KeyDataService keyDataService;

    @GetMapping("/{id}")
    public KeyDTO getKey(@PathVariable Long id) {
        return toDTO(keyDataService.getKeyById(id));
    }

    @GetMapping
    public List<KeyDTO> getAllKeys(@RequestParam(required = false) Long ownerId,
                                   @RequestParam(required = false) Long homeUserId) {
        if (ownerId != null) {
            return keyDataService.getKeysByOwnerId(ownerId).stream()
                    .map(this::toDTO)
                    .toList();
        }
        if (homeUserId != null) {
            return keyDataService.getKeysByHomeUserId(homeUserId).stream()
                    .map(this::toDTO)
                    .toList();
        }
        throw new IllegalArgumentException("ownerId or homeUserId is required");
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
        if (key.getHome().getOwner().getAdmin() != null) {
            dto.setAdminId(key.getHome().getOwner().getAdmin().getId());
        }
        if (key.getHomeUser() != null) {
            dto.setHomeUserId(key.getHomeUser().getId());
            dto.setHomeUserName(key.getHomeUser().getUser().getFullName());
        }
        return dto;
    }
}
