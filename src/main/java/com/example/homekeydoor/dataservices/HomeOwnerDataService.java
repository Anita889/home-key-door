package com.example.homekeydoor.dataservices;


import com.example.homekeydoor.dtos.HomeOwnerDTO;
import com.example.homekeydoor.entities.HomeOwner;
import com.example.homekeydoor.repositories.HomeOwnerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeOwnerDataService {
    @Autowired
    private HomeOwnerRepository homeOwnerRepository;

    public List<HomeOwner> getAllHomeOwnersByAdminId(Long adminId) {
        return homeOwnerRepository.findAllByAdminId(adminId);
    }

    public HomeOwner createHomeOwner(Long adminId, HomeOwner owner) {
        return null;
    }

    public HomeOwner updateHomeOwner(Long ownerId, HomeOwnerDTO dto) {
        return null;
    }

    public void deleteHomeOwner(Long ownerId) {
        homeOwnerRepository.deleteById(ownerId);
    }
}
