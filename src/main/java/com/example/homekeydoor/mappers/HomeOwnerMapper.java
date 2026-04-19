package com.example.homekeydoor.mappers;

import com.example.homekeydoor.dtos.HomeOwnerDTO;
import com.example.homekeydoor.entities.HomeOwner;
import com.example.homekeydoor.entities.User;
import org.springframework.stereotype.Component;


@Component
public class HomeOwnerMapper {
    public HomeOwnerDTO toDTO(HomeOwner owner) {
        HomeOwnerDTO dto = new HomeOwnerDTO();
        dto.setId(owner.getId());
        dto.setFirstName(owner.getUser().getFirstName());
        dto.setSecondName(owner.getUser().getLastName());
        dto.setEmail(owner.getUser().getEmail());
        return dto;
    }

    public HomeOwner toEntity(HomeOwnerDTO dto) {
        HomeOwner owner = new HomeOwner();
        owner.setId(dto.getId());
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getSecondName());
        user.setEmail(dto.getEmail());
        owner.setUser(user);
        return owner;
    }
}
