package com.example.homekeydoor.dataservices;


import com.example.homekeydoor.dtos.HomeUserDTO;
import com.example.homekeydoor.entities.HomeUser;
import com.example.homekeydoor.entities.Key;
import com.example.homekeydoor.entities.User;
import jakarta.persistence.EntityNotFoundException;
import com.example.homekeydoor.repositories.HomeUserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeUserDataService {
    @Autowired
    private HomeUserRepository homeUserRepository;

    public List<HomeUser> getAllHomeUsersByAdminId(Long adminId) {
        return homeUserRepository.findAllByRemovedFalse().stream()
                .filter(homeUser -> hasAdminAccess(homeUser, adminId))
                .toList();
    }

    public HomeUser createHomeUser(Long adminId, HomeUser user) {
        if (user.getUser() == null) {
            user.setUser(new User());
        }
        return homeUserRepository.save(user);
    }

    public void deleteHomeUser(Long userId) {
        homeUserRepository.deleteById(userId);
    }

    public HomeUser updateHomeUser(Long userId, HomeUserDTO dto) {
        HomeUser user = getHomeUserById(userId);
        user.getUser().setFirstName(dto.getFirstName());
        user.getUser().setLastName(dto.getSecondName());
        user.getUser().setEmail(dto.getEmail());
        return homeUserRepository.save(user);
    }

    public HomeUser getHomeUserById(Long userId) {
        return homeUserRepository.findById(userId)
                .filter(homeUser -> !homeUser.isRemoved())
                .orElseThrow(() -> new EntityNotFoundException("Home user not found with id: " + userId));
    }

    private boolean hasAdminAccess(HomeUser homeUser, Long adminId) {
        List<Key> keys = homeUser.getKeys();
        if (keys == null || keys.isEmpty()) {
            return false;
        }
        return keys.stream()
                .map(Key::getHome)
                .filter(home -> home != null && home.getOwner() != null)
                .anyMatch(home -> home.getOwner().getAdmin() != null
                        && adminId.equals(home.getOwner().getAdmin().getId()));
    }
}
