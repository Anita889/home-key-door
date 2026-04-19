package com.example.homekeydoor.mappers;


import com.example.homekeydoor.dtos.RoleDTO;
import com.example.homekeydoor.dtos.UserDTO;
import com.example.homekeydoor.entities.User;
import com.example.homekeydoor.entities.base.Role;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO userDTOFromUserEntity(User userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setFirstName(userEntity.getFirstName());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setRoles(roleDTOFromRoleEntity(userEntity.getRoles()));
        return userDTO;
    }

    private Set<RoleDTO> roleDTOFromRoleEntity(Set<Role> roles) {
        //TODO
        return roles.stream().map(role -> {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setId(role.getId());
            roleDTO.setName(role.getName());
            return roleDTO;
        }).collect(java.util.stream.Collectors.toSet());
    }
}
