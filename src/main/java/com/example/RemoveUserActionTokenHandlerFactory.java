package com.example;

import org.keycloak.Config;
import org.keycloak.authentication.actiontoken.ActionTokenHandler;
import org.keycloak.authentication.actiontoken.ActionTokenHandlerFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class RemoveUserActionTokenHandlerFactory implements ActionTokenHandlerFactory<RemoveUserActionToken> {


    @Override
    public ActionTokenHandler<RemoveUserActionToken> create(KeycloakSession session) {
        return new RemoveUserActionTokenHandler();
    }

    @Override
    public void init(Config.Scope config) {
        System.out.println("++++++++++++++++++++++action token handler factory init++++++++++++++++++");

    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return RemoveUserActionTokenHandler.ID;
    }
}
