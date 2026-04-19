package com.example.homekeydoor.dataservices;


import com.example.homekeydoor.dtos.HomeOwnerDTO;
import com.example.homekeydoor.entities.HomeOwner;
import com.example.homekeydoor.entities.User;
import jakarta.persistence.EntityNotFoundException;
import com.example.homekeydoor.repositories.HomeOwnerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeOwnerDataService {
    @Autowired
    private HomeOwnerRepository homeOwnerRepository;

    @Autowired
    private AdminDataService adminDataService;

    public List<HomeOwner> getAllHomeOwnersByAdminId(Long adminId) {
        return homeOwnerRepository.findAllByAdminIdAndRemovedFalse(adminId);
    }

    public HomeOwner getHomeOwnerById(Long ownerId) {
        return homeOwnerRepository.findById(ownerId)
                .filter(owner -> !owner.isRemoved())
                .orElseThrow(() -> new EntityNotFoundException("Home owner not found with id: " + ownerId));
    }

    public HomeOwner createHomeOwner(Long adminId, HomeOwner owner) {
        if (owner.getUser() == null) {
            owner.setUser(new User());
        }
        owner.setAdmin(adminDataService.getAdminById(adminId));
        return homeOwnerRepository.save(owner);
    }

    public HomeOwner updateHomeOwner(Long ownerId, HomeOwnerDTO dto) {
        HomeOwner owner = getHomeOwnerById(ownerId);
        owner.getUser().setFirstName(dto.getFirstName());
        owner.getUser().setLastName(dto.getSecondName());
        owner.getUser().setEmail(dto.getEmail());
        return homeOwnerRepository.save(owner);
    }

    public void deleteHomeOwner(Long ownerId) {
        homeOwnerRepository.deleteById(ownerId);
    }

    public HomeOwner grantAdminAccess(Long ownerId, Long adminId) {
        HomeOwner owner = getHomeOwnerById(ownerId);
        owner.setAdmin(adminDataService.getAdminById(adminId));
        return homeOwnerRepository.save(owner);
    }
}
