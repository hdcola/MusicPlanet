package com.jac.fsd.musicplanet.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

import java.net.URI;

@Configuration
public class SpotifyConfig {
    @Value("${spotify.clientId}")
    private String clientId;
    @Value("${spotify.clientSecret}")
    private String clientSecret;
    @Value("${spotify.redirectUri}")
    private String redirectUri;

    @Bean
    public SpotifyApi spotifyApi() {
        URI redirect = SpotifyHttpManager.makeUri(redirectUri);
        return new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRedirectUri(redirect)
                .build();
    }
}
