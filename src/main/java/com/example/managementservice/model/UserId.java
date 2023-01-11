package com.example.managementservice.model;

import com.example.managementservice.exception.IdNotFoundException;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.IDToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class UserId {

    private String userId;

    public String getUserId() {
        this.userId = getUserIdFromToken();
        return this.userId;
    }

    private String getUserIdFromToken() throws IdNotFoundException {
        try {
            KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            Principal principal = (Principal) authentication.getPrincipal();
            KeycloakSecurityContext keycloakSecurityContext = ((KeycloakPrincipal<?>) principal).getKeycloakSecurityContext();
            IDToken userToken = keycloakSecurityContext.getToken();
            return userToken.getSubject();
        } catch(Exception e) {
            throw new IdNotFoundException();
        }
    }
}
