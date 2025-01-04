package com.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.keycloak.authentication.actiontoken.DefaultActionToken;

public class RemoveUserActionToken extends DefaultActionToken {

    public static final String TOKEN_TYPE = "remove-user-token";

    private static final String REALM_NAME = "realm-name";

    @JsonProperty(value = REALM_NAME)
    private String realmName;

    public RemoveUserActionToken(String userId, int absoluteExpirationInSecs, String authenticationSessionId, String realmName) {
        super(userId, TOKEN_TYPE, absoluteExpirationInSecs, null, authenticationSessionId);
        this.realmName = realmName;
    }

    private RemoveUserActionToken() {
    }

    public String getRealmName() {
        return realmName;
    }

    public void setRealmName(String realmName) {
        this.realmName = realmName;
    }
}
