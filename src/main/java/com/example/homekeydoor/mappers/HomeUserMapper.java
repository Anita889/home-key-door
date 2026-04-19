package com.example.homekeydoor.mappers;


import com.example.homekeydoor.dtos.HomeUserDTO;
import com.example.homekeydoor.entities.HomeUser;
import com.example.homekeydoor.entities.User;
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
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getSecondName());
        user.setEmail(dto.getEmail());
        obj.setUser(user);
        return obj;
    }
}
