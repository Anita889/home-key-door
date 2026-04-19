package com.example.homekeydoor.entities;


import com.example.homekeydoor.consts.KeyStatus;
import com.example.homekeydoor.entities.base.AbstractRemovableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Table(name = "keys")
public class Key extends AbstractRemovableEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Enumerated(EnumType.STRING)
    private KeyStatus status;

    @ManyToOne
    @JoinColumn(name = "home_id", nullable = false)
    private Home home;

    @ManyToOne
    @JoinColumn(name = "home_user_id", nullable = false)
    private HomeUser homeUser;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
      this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public KeyStatus getStatus() {
        return status;
    }

    public void setStatus(KeyStatus status) {
        this.status = status;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public HomeUser getHomeUser() {
        return homeUser;
    }

    public void setHomeUser(HomeUser homeUser) {
        this.homeUser = homeUser;
    }
}
