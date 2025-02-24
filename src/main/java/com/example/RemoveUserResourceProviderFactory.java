package com.example;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.services.resource.RealmResourceProviderFactory;

public class RemoveUserResourceProviderFactory implements RealmResourceProviderFactory {


    @Override
    public RealmResourceProvider create(KeycloakSession session) {
        return new ActionTokenApiResourceProvider(session);
    }

    @Override
    public void init(Config.Scope config) {
        System.out.println("++++++++++++++++++++resource provider factory init++++++++++++++++++++");


    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return ActionTokenApiResourceProvider.ID;
    }
}
