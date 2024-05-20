package org.defalt.content.context.auth;

import jakarta.ws.rs.core.Response;
import org.defalt.content.context.CurrentApplicationContext;
import org.defalt.content.context.auth.exception.UserNotRegisteredException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KeyCloakAdmin {
    protected final Keycloak client;

    protected KeyCloakAdmin(@Value("${keycloak-server.url}") String url,
                            @Value("${keycloak-server.username}") String username,
                            @Value("${keycloak-server.password}") String password,
                            @Value("${keycloak-server.client-secret}") String clientSecret) {

        client = KeycloakBuilder.builder()
                .serverUrl(url)
                .realm("master")
                .clientId("admin-cli")
                .clientSecret(clientSecret)
                .username(username)
                .password(password)
                .build();
    }

    public static KeyCloakAdmin getInstance() {
        return CurrentApplicationContext.getBean(KeyCloakAdmin.class);
    }

    public UserRepresentation getUser(String username) {
        List<UserRepresentation> result = this.client.realm("osm").users().searchByUsername(username, true);
        if (result.size() == 1)
            return result.get(0);
        else if (result.size() == 0)
            throw new UserNotRegisteredException("no user found with given username.");
        else
            throw new RuntimeException("keycloak is in illegal state, more than one user exist for given username.");
    }
}
