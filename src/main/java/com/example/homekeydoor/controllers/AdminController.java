package com.example.homekeydoor.controllers;

import com.example.homekeydoor.dataservices.*;
import com.example.homekeydoor.dtos.*;
import com.example.homekeydoor.entities.*;
import com.example.homekeydoor.mappers.*;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final HomeOwnerDataService homeOwnerDataService;
    private final HomeUserDataService homeUserDataService;
    private final AdminDataService adminService;

    private final HomeOwnerMapper ownerMapper;
    private final HomeUserMapper homeUserMapper;

    @GetMapping("/{adminId}/dashboard")
    public ResponseEntity<AdminDTO> dashboard(@PathVariable Long adminId) {
        Admin admin = adminService.getAdminById(adminId);

        AdminDTO dto = new AdminDTO();
        dto.setId(admin.getId());
        dto.setFirstName(admin.getUser().getFirstName());
        dto.setLastName(admin.getUser().getLastName());
        dto.setEmail(admin.getUser().getEmail());

        return ResponseEntity.ok(dto);
    }


    @GetMapping("/{adminId}/owners")
    public ResponseEntity<List<HomeOwnerDTO>> getOwners(@PathVariable Long adminId) {
        return ResponseEntity.ok(
                homeOwnerDataService.getAllHomeOwnersByAdminId(adminId)
                        .stream()
                        .map(ownerMapper::toDTO)
                        .toList()
        );
    }

    @GetMapping("/{adminId}/homeUsers")
    public ResponseEntity<List<HomeUserDTO>> getHomeUsers(@PathVariable Long adminId) {
        return ResponseEntity.ok(
                homeUserDataService.getAllHomeUsersByAdminId(adminId)
                        .stream()
                        .map(homeUserMapper::toDTO)
                        .toList()
        );
    }


    @PostMapping("/{adminId}/owners")
    public ResponseEntity<HomeOwnerDTO> createHomeOwner(
            @PathVariable Long adminId,
            @RequestBody HomeOwnerDTO dto) {

        HomeOwner owner = ownerMapper.toEntity(dto);
        HomeOwner saved = homeOwnerDataService.createHomeOwner(adminId, owner);

        return ResponseEntity.ok(ownerMapper.toDTO(saved));
    }

    @PostMapping("/{adminId}/homeUsers")
    public ResponseEntity<HomeUserDTO> createHomeUser(
            @PathVariable Long adminId,
            @RequestBody HomeUserDTO dto) {

        HomeUser user = homeUserMapper.toEntity(dto);
        HomeUser saved = homeUserDataService.createHomeUser(adminId, user);

        return ResponseEntity.ok(homeUserMapper.toDTO(saved));
    }


    @PutMapping("/owners/{ownerId}")
    public ResponseEntity<HomeOwnerDTO> updateHomeOwner(
            @PathVariable Long ownerId,
            @RequestBody HomeOwnerDTO dto) {

        HomeOwner updated = homeOwnerDataService.updateHomeOwner(ownerId, dto);
        return ResponseEntity.ok(ownerMapper.toDTO(updated));
    }

    @PutMapping("/homeUsers/{userId}")
    public ResponseEntity<HomeUserDTO> updateHomeUser(
            @PathVariable Long userId,
            @RequestBody HomeUserDTO dto) {

        HomeUser updated = homeUserDataService.updateHomeUser(userId, dto);
        return ResponseEntity.ok(homeUserMapper.toDTO(updated));
    }


    @DeleteMapping("/owners/{ownerId}")
    public ResponseEntity<Void> deleteHomeOwner(@PathVariable Long ownerId) {
        homeOwnerDataService.deleteHomeOwner(ownerId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/homeUsers/{userId}")
    public ResponseEntity<Void> deleteHomeUser(@PathVariable Long userId) {
        homeUserDataService.deleteHomeUser(userId);
        return ResponseEntity.noContent().build();
    }
}