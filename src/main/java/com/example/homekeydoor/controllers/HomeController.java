package com.example.homekeydoor.controllers;

import com.example.homekeydoor.dataservices.HomeDataService;
import com.example.homekeydoor.dtos.HomeDTO;
import com.example.homekeydoor.entities.Home;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/homes")
@RequiredArgsConstructor
public class HomeController {

    private final HomeDataService homeDataService;

    @GetMapping("/{id}")
    public HomeDTO getHome(@PathVariable Long id) {
        return toDTO(homeDataService.getHomeById(id));
    }

    @GetMapping
    public List<HomeDTO> getAllHomes(@RequestParam Long ownerId) {
        return homeDataService.getHomesByOwnerId(ownerId).stream()
                .map(this::toDTO)
                .toList();
    }

    private HomeDTO toDTO(Home home) {
        HomeDTO dto = new HomeDTO();
        dto.setId(home.getId());
        dto.setName(home.getName());
        dto.setOwnerId(home.getOwner().getId());
        dto.setOwnerName(home.getOwner().getUser().getFullName());
        return dto;
    }
}
