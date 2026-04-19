package com.example.homekeydoor.dataservices;


import com.example.homekeydoor.dtos.HomeUserDTO;
import com.example.homekeydoor.entities.HomeUser;
import com.example.homekeydoor.repositories.HomeUserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeUserDataService {
    @Autowired
    private HomeUserRepository homeUserRepository;

    public List<HomeUser> getAllHomeUsersByAdminId(Long adminId) {
        return homeUserRepository.findAllByHomesAdminId(adminId);
    }

    public HomeUser createHomeUser(Long adminId, HomeUser user) {
        return homeUserRepository.save(user);
    }

    public void deleteHomeUser(Long userId) {
        homeUserRepository.deleteById(userId);
    }

    public HomeUser updateHomeUser(Long userId, HomeUserDTO dto) {
        return  null;
    }
}
