package com.example.homekeydoor.security;

import com.example.homekeydoor.consts.RegistrationType;
import com.example.homekeydoor.dataservices.UserDataService;
import com.example.homekeydoor.entities.User;
import com.example.homekeydoor.entities.base.Privilege;
import com.example.homekeydoor.entities.base.Role;
import com.example.homekeydoor.repositories.RoleRepository;
import com.example.homekeydoor.repositories.UserRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userEntity = userDataService.findByEmailAndRegistrationType(email, RegistrationType.REGISTERED);
//		if (userEntity == null) {
//			throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
//		} else {
//			return new SecureUser(userEntity, AuthorityUtils.commaSeparatedStringToAuthorityList(userEntity.getAuthorities()));
//		}

        if (userEntity == null) {
            throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
        }

        return new SecureUser(userEntity, getAuthorities(userEntity.getRoles()));

//		return new org.springframework.security.core.userdetails.User(
//				userEntity.getEmail(), userEntity.getPassword(), !userEntity.isLocked(), true, true,
//				true, getAuthorities(userEntity.getRoles()));

    }

    private Collection<GrantedAuthority> getAuthorities(
            Collection<Role> roles) {

        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Role> roles) {

        List<String> privileges = new ArrayList<>();
        Set<Privilege> collection = new HashSet<>();
        for (Role role : roles) {
            collection.addAll(role.getPrivileges());
            privileges.add(role.getName());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

}
