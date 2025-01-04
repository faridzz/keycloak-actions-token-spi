package com.example;

public class Input {

    private String userId;
    private String realmName;
    private String clientId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId.trim().toLowerCase();
    }


    public String getRealmName() {
        return realmName;
    }

    public void setRealmName(String realmName) {
        this.realmName = realmName.trim().toLowerCase();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId.trim().toLowerCase();
    }
}
