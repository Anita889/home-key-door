package com.example.homekeydoor.consts;

public enum UserType {
    ADMIN("ADMIN"),
    HOME_OWNER("HOME_OWNER"),
    HOME_USER("HOME_USER");

    private String label;

    UserType(String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static UserType getByLabel(String label) {
        for (UserType userType : values()) {
            if (userType.getLabel().equals(label)) {
                return userType;
            }
        }
        return null;
    }

}

