package com.example.homekeydoor.dataservices;

import com.example.homekeydoor.entities.Admin;
import jakarta.persistence.EntityNotFoundException;
import com.example.homekeydoor.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminDataService {
    @Autowired
    private AdminRepository adminRepository;

    public Admin getAdminById(Long adminId) {
        return adminRepository.findById(adminId)
                .filter(admin -> !admin.isRemoved())
                .orElseThrow(() -> new EntityNotFoundException("Admin not found with id: " + adminId));
    }
}
