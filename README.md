
# Keycloak SPI - Custom Action Token API

This is a Keycloak Service Provider Interface (SPI) that provides an API for generating custom action tokens. With this SPI, a user can be removed from a specific realm in Keycloak by generating an action token through a custom API endpoint.

## API Overview

### Generate Action Token

To generate an action token, send a `POST` request to the following endpoint:

```
{base-url}/realms/{realm-name}/user-remove-action-token/generate-token
```

**Request Body:**

```json
{
  "userId": "a7aeada6-4c0e-42ca-9702-064ab58bd6fb",
  "realmName": "test-realm",
  "clientId": "test-client"
}
```

### Action Token Handler

Once the action token is generated, it can be used to trigger the removal of a specified user from the realm. The action token handler URL looks like this:

```
{base-url}/realms/{realm-name}/login-actions/action-token?key=......
```

---

This SPI provides a seamless integration with Keycloak's powerful authentication and token management system, making user removal tasks efficient and secure.
