package com.example.homekeydoor.mappers;


import com.example.homekeydoor.dtos.HomeUserDTO;
import com.example.homekeydoor.entities.HomeOwner;
import com.example.homekeydoor.entities.HomeUser;
import org.springframework.stereotype.Component;

@Component
public class HomeUserMapper {
    public HomeUserDTO toDTO(HomeUser obj) {
        HomeUserDTO dto = new HomeUserDTO();
        dto.setId(obj.getId());
        dto.setFirstName(obj.getUser().getFirstName());
        dto.setSecondName(obj.getUser().getLastName());
        dto.setEmail(obj.getUser().getEmail());
        return dto;
    }

    public HomeUser toEntity(HomeUserDTO dto) {
        HomeUser obj = new HomeUser();
        obj.setId(dto.getId());
        obj.getUser().setFirstName(dto.getFirstName());
        obj.getUser().setLastName(dto.getSecondName());
        obj.getUser().setEmail(dto.getEmail());
        return obj;
    }
}
