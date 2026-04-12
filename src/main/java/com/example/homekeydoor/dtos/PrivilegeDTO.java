package com.example.homekeydoor.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrivilegeDTO  extends AbstractDTO{

    private Long id;
    private String name;
    private String description;
    private String displayName;
    private String userScope;
    protected boolean storeRequired;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUserScope() {
        return userScope;
    }

    public void setUserScope(String userScope) {
        this.userScope = userScope;
    }

    public boolean isStoreRequired() {
        return storeRequired;
    }

    public void setStoreRequired(boolean storeRequired) {
        this.storeRequired = storeRequired;
    }
}

