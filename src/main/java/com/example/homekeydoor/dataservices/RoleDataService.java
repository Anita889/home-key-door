package com.example.homekeydoor.dataservices;



import com.example.homekeydoor.entities.base.Role;
import com.example.homekeydoor.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleDataService {

    @Autowired
    private RoleRepository roleRepository;

    public Role findByName(String roleAdmin) {
        return roleRepository.findByName(roleAdmin);
    }
}

