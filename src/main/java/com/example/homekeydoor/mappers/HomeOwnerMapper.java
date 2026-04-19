package com.example.homekeydoor.mappers;

import com.example.homekeydoor.dtos.HomeOwnerDTO;
import com.example.homekeydoor.entities.HomeOwner;
import org.springframework.stereotype.Component;


@Component
public class HomeOwnerMapper {
    public HomeOwnerDTO toDTO(HomeOwner owner) {
        HomeOwnerDTO dto = new HomeOwnerDTO();
        dto.setId(owner.getId());
        dto.setFirstName(owner.getUser().getFirstName());
        dto.setEmail(owner.getUser().getEmail());
        return dto;

    }

    public HomeOwner toEntity(HomeOwnerDTO dto) {
        HomeOwner owner = new HomeOwner();
        owner.setId(dto.getId());
        owner.getUser().setFirstName(dto.getFirstName());
        owner.getUser().setEmail(dto.getEmail());
        return owner;
    }
}
