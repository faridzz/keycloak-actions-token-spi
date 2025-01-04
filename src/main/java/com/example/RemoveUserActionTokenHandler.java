package com.example;

import jakarta.ws.rs.core.Response;
import org.keycloak.authentication.actiontoken.AbstractActionTokenHandler;
import org.keycloak.authentication.actiontoken.ActionTokenContext;
import org.keycloak.events.Errors;
import org.keycloak.events.EventType;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

/**
 * Handler for processing RemoveUserActionToken.
 */
public class RemoveUserActionTokenHandler extends AbstractActionTokenHandler<RemoveUserActionToken> {
    static final String ID = RemoveUserActionToken.TOKEN_TYPE;

    public RemoveUserActionTokenHandler() {
        super(
                RemoveUserActionToken.TOKEN_TYPE,
                RemoveUserActionToken.class,
                "User not found or Token expired/invalid",
                EventType.EXECUTE_ACTION_TOKEN,
                Errors.INVALID_REQUEST
        );
    }

    @Override
    public Response handleToken(RemoveUserActionToken token, ActionTokenContext<RemoveUserActionToken> context) {
        System.out.println("++++++++++++++++++++++action token handler handle token ... ++++++++++++++++++++++");
        // Validate the token and retrieve necessary details
        if (token == null) {
            return buildErrorResponse(Response.Status.BAD_REQUEST, "Invalid token provided.");
        }

        String userId = token.getUserId();
        if (userId == null || userId.isEmpty()) {
            return buildErrorResponse(Response.Status.BAD_REQUEST, "User ID is missing in the token.");
        }

        RealmModel realm = context.getRealm();
        String realmName = realm.getName();
        KeycloakSession session = context.getSession();

        if (token.getRealmName() == null || !realmName.equals(token.getRealmName())) {
            return buildErrorResponse(Response.Status.BAD_REQUEST, "Realm name mismatch.");
        }

        UserModel user = session.users().getUserById(realm, userId);
        if (user == null) {
            return buildErrorResponse(Response.Status.NOT_FOUND, "User not found.");
        }

        try {
            // Perform user removal
            session.users().removeUser(realm, user);
            return Response.ok("User removed successfully.").build();
        } catch (Exception ex) {
            // Handle any unexpected errors during user removal
            return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, "Error occurred while removing the user.");
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
                .entity(message)
                .build();
    }
}
