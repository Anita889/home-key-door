package com.example.homekeydoor.dtos;

import com.example.homekeydoor.consts.KeyStatus;

public class KeyDTO {
    private Long id;
    private String code;
    private KeyStatus status;
    private Long homeId;
    private String homeName;
    private Long homeUserId;
    private String homeUserName;
    private Long ownerId;
    private String ownerName;
    private Long adminId;

    public Long getId() {
        return id;
    }

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

    public Long getHomeId() {
        return homeId;
    }

    public void setHomeId(Long homeId) {
        this.homeId = homeId;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public Long getHomeUserId() {
        return homeUserId;
    }

    public void setHomeUserId(Long homeUserId) {
        this.homeUserId = homeUserId;
    }

    public String getHomeUserName() {
        return homeUserName;
    }

    public void setHomeUserName(String homeUserName) {
        this.homeUserName = homeUserName;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }
}
