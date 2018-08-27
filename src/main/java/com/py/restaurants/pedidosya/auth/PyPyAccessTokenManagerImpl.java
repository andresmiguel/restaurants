package com.py.restaurants.pedidosya.auth;

import com.py.restaurants.pedidosya.exceptions.PyAccessTokenException;
import com.py.restaurants.utils.JsonUtils;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@Component
public class PyPyAccessTokenManagerImpl implements PyAccessTokenManager {

    @Value("${py.auth.tokensUrl}")
    private String accessTokenUrl;
    @Value("${py.auth.clientId}")
    private String clientId;
    @Value("${py.auth.clientSecret}")
    private String clientSecret;

    private volatile String accessToken;

    private RestTemplate restTemplate;

    public PyPyAccessTokenManagerImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String getToken() throws PyAccessTokenException {
        if (accessToken == null) {
            refreshToken();
        }
        return accessToken;
    }

    @Override
    public void refreshToken() throws PyAccessTokenException {
        URI uri = getAccessTokenUri();
        try {
            ResponseEntity<String> entity = restTemplate.getForEntity(uri, String.class);
            Token token = JsonUtils.toObject(entity.getBody(), Token.class);
            accessToken = token.access_token;
        } catch (RestClientException rce) {
            String message = ": " + rce.getMessage();
            throw new PyAccessTokenException(message, rce);
        } catch (IOException e) {
            String message = "Error parsing access token response";
            throw new PyAccessTokenException(message, e);
        }
    }

    private URI getAccessTokenUri() {
        return UriComponentsBuilder.fromHttpUrl(accessTokenUrl)
                .queryParam("clientId", clientId)
                .queryParam("clientSecret", clientSecret)
                .build()
                .toUri();
    }

    @Setter
    private static class Token {
        public String access_token;
    }
}
