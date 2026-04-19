package com.example.homekeydoor.dataservices;


import com.example.homekeydoor.entities.Home;
import com.example.homekeydoor.entities.HomeOwner;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import com.example.homekeydoor.repositories.HomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeDataService {

    @Autowired
    private HomeRepository homeRepository;

    public Home createHome(HomeOwner owner, String homeName) {
        Home home = new Home();
        home.setName(homeName);
        home.setOwner(owner);
        return homeRepository.save(home);
    }

    public List<Home> getHomesByOwnerId(Long ownerId) {
        return homeRepository.findAllByOwnerIdAndRemovedFalse(ownerId);
    }

    public Home getHomeById(Long homeId) {
        return homeRepository.findById(homeId)
                .filter(home -> !home.isRemoved())
                .orElseThrow(() -> new EntityNotFoundException("Home not found with id: " + homeId));
    }

    public Home getHomeByIdAndOwnerId(Long homeId, Long ownerId) {
        Home home = getHomeById(homeId);
        if (!home.getOwner().getId().equals(ownerId)) {
            throw new EntityNotFoundException("Home not found for owner id: " + ownerId);
        }
        return home;
    }
}
