package com.example;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.keycloak.common.util.Time;
import org.keycloak.models.KeycloakContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

/**
 * API for generating action tokens.
 */
public class ActionTokenApiResourceProvider implements RealmResourceProvider {

    private final KeycloakSession session;

    static final String ID = "user-remove-action-token";

    /**
     * Constructor to initialize the Keycloak session.
     *
     * @param session Keycloak session instance
     */
    public ActionTokenApiResourceProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public Object getResource() {
        return this;
    }

    @Override
    public void close() {
        // Nothing to close
    }

    /**
     * Generates an action token for the given input parameters.
     *
     * @param input   Input data containing token details
     * @param uriInfo UriInfo for building token links
     * @return JSON response with the generated token
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("generate-token")
    public Response getActionToken(@RequestBody Input input, @Context UriInfo uriInfo) {
        if (input == null || input.getUserId() == null || input.getUserId().isEmpty()) {
            return buildErrorResponse(Response.Status.BAD_REQUEST, "User ID is missing or invalid.");
        }

        if (input.getClientId() == null || input.getClientId().isEmpty()) {
            return buildErrorResponse(Response.Status.BAD_REQUEST, "Client ID is missing or invalid.");
        }

        if (input.getRealmName() == null || input.getRealmName().isEmpty()) {
            return buildErrorResponse(Response.Status.BAD_REQUEST, "Realm name is missing or invalid.");
        }

        try {
            KeycloakContext context = session.getContext();

            // Calculate token expiration
            int validityInSecs = context.getRealm().getActionTokenGeneratedByUserLifespan();
            int absoluteExpirationInSecs = Time.currentTime() + validityInSecs;

            // Generate token
            String token = new RemoveUserActionToken(
                    input.getUserId(),
                    absoluteExpirationInSecs,
                    input.getClientId(),
                    input.getRealmName()
            ).serialize(session, context.getRealm(), uriInfo);

            return Response.ok(token)
                    .header("Cache-Control", "no-cache, no-store, must-revalidate")
                    .header("Pragma", "no-cache")
                    .header("Expires", "0")
                    .build();

        } catch (Exception ex) {
            // Log and return error response
            ex.printStackTrace();
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, "An error occurred while generating the token.");
        }
    }

    /**
     * Utility method to build error responses with a specific message.
     *
     * @param status  HTTP response status
     * @param message Error message
     * @return Response with error details
     */
    private Response buildErrorResponse(Response.Status status, String message) {
        return Response.status(status)
                .entity("{\"error\":\"" + message + "\"}")
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
