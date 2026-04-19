package com.example.homekeydoor.controllers;

import com.example.homekeydoor.dataservices.AdminDataService;
import com.example.homekeydoor.dataservices.HomeDataService;
import com.example.homekeydoor.dataservices.HomeOwnerDataService;
import com.example.homekeydoor.dataservices.KeyDataService;
import com.example.homekeydoor.dtos.CreateHomeRequestDTO;
import com.example.homekeydoor.dtos.CreateKeyRequestDTO;
import com.example.homekeydoor.dtos.HomeDTO;
import com.example.homekeydoor.dtos.KeyDTO;
import com.example.homekeydoor.entities.Home;
import com.example.homekeydoor.entities.HomeOwner;
import com.example.homekeydoor.entities.Key;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/owner")
@RequiredArgsConstructor
@PreAuthorize("hasRole('HOME_OWNER')")
public class HomeOwnerController {

    private final HomeOwnerDataService homeOwnerDataService;
    private final HomeDataService homeDataService;
    private final KeyDataService keyDataService;
    private final AdminDataService adminDataService;

    @PostMapping("/{ownerId}/homes")
    public HomeDTO createHome(@PathVariable Long ownerId, @RequestBody CreateHomeRequestDTO request) {
        HomeOwner owner = homeOwnerDataService.getHomeOwnerById(ownerId);
        return toHomeDTO(homeDataService.createHome(owner, request.getName()));
    }

    @GetMapping("/{ownerId}/homes")
    public List<HomeDTO> getMyHomes(@PathVariable Long ownerId) {
        homeOwnerDataService.getHomeOwnerById(ownerId);
        return homeDataService.getHomesByOwnerId(ownerId).stream()
                .map(this::toHomeDTO)
                .toList();
    }

    @PostMapping("/{ownerId}/admins/{adminId}/access")
    public HomeDTO giveAdminAccess(@PathVariable Long ownerId, @PathVariable Long adminId) {
        adminDataService.getAdminById(adminId);
        HomeOwner owner = homeOwnerDataService.grantAdminAccess(ownerId, adminId);
        Home home = homeDataService.getHomesByOwnerId(owner.getId()).stream().findFirst().orElse(null);
        if (home == null) {
            HomeDTO dto = new HomeDTO();
            dto.setOwnerId(owner.getId());
            dto.setOwnerName(owner.getUser().getFullName());
            return dto;
        }
        return toHomeDTO(home);
    }

    @GetMapping("/{ownerId}/keys")
    public List<KeyDTO> seeWhoHasKeys(@PathVariable Long ownerId) {
        homeOwnerDataService.getHomeOwnerById(ownerId);
        return keyDataService.getKeysByOwnerId(ownerId).stream()
                .map(this::toKeyDTO)
                .toList();
    }

    @PostMapping("/{ownerId}/homes/{homeId}/keys")
    public KeyDTO createKey(@PathVariable Long ownerId,
                            @PathVariable Long homeId,
                            @RequestBody CreateKeyRequestDTO request) {
        Home home = homeDataService.getHomeByIdAndOwnerId(homeId, ownerId);
        return toKeyDTO(keyDataService.createKey(home, request.getCode()));
    }

    private HomeDTO toHomeDTO(Home home) {
        HomeDTO dto = new HomeDTO();
        dto.setId(home.getId());
        dto.setName(home.getName());
        dto.setOwnerId(home.getOwner().getId());
        dto.setOwnerName(home.getOwner().getUser().getFullName());
        return dto;
    }

    private KeyDTO toKeyDTO(Key key) {
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
