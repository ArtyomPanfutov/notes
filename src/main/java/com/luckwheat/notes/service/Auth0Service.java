package com.luckwheat.notes.service;

import com.luckwheat.notes.dto.auth0.UserInfo;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.uri.UriBuilder;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.net.URI;

@Singleton
public class Auth0Service {

    private static final URI USERINFO_URI = UriBuilder.of("/userinfo").build();

    private final HttpClient httpClient;

    @Inject
    public Auth0Service(@Client("${micronaut.security.token.jwt.claims-validators.issuer}")
                               HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public UserInfo fetchUserInfo(String token) {
        var request = HttpRequest.GET(USERINFO_URI)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        return httpClient.toBlocking().retrieve(request, Argument.of(UserInfo.class));
    }
}
