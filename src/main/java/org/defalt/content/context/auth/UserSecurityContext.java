package org.defalt.content.context.auth;

import org.defalt.content.entity.User;
import org.defalt.content.service.UserService;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.jwt.Jwt;

public class UserSecurityContext {
    private final SecurityContextImpl context;
    private final String username;
    private UserRepresentation keycloakUser;

    protected UserSecurityContext(SecurityContextImpl context) {
        this.context = context;
        Object principal = context.getAuthentication().getPrincipal();
        if (principal instanceof Jwt) {
            this.username = ((Jwt) principal).getClaim("preferred_username");
        }
        else {
            this.username = null;
        }

    }

    public String getUsername() {
        return username;
    }

    public static UserSecurityContext getCurrentUser() {
        return new UserSecurityContext((SecurityContextImpl) SecurityContextHolder.getContext());
    }

    public UserRepresentation getKeycloakUser() {
        if (this.keycloakUser == null && this.username != null)
            return this.keycloakUser = KeyCloakAdmin.getInstance().getUser(this.username);
        else
            return this.keycloakUser;
    }

    public SecurityContextImpl getContext() {
        return context;
    }

    public User getUser() {
        return UserService.getInstance().getOrCreate(username);
    }
}
