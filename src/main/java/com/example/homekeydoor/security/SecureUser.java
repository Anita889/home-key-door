package com.example.homekeydoor.security;


import com.example.homekeydoor.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class SecureUser implements UserDetails {

    private User userEntity;
    private Collection<GrantedAuthority> grantedAuthorities;

    public SecureUser() {
        super();
    }

    public SecureUser(User userEntity, Collection<GrantedAuthority> grantedAuthorities) {
        this.userEntity = userEntity;
        this.grantedAuthorities = grantedAuthorities;
    }

    public Long getId() {
        return userEntity.getId();
    }

    public User getUserEntity() {
        return userEntity;
    }

    public String getUsername() {
        return userEntity.getUsername();
    }

    @JsonIgnore
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

