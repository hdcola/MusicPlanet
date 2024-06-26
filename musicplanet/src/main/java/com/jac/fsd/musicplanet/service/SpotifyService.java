package com.jac.fsd.musicplanet.service;

import com.jac.fsd.musicplanet.DTO.UserDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;

import java.net.URI;
import java.time.LocalDateTime;

@Service
@Log4j2
public class SpotifyService {
    @Value("${spotify.clientId}")
    private String clientId;
    @Value("${spotify.clientSecret}")
    private String clientSecret;
    @Value("${spotify.redirectUri}")
    private String redirectUri;

    private final String username = "danny";

    UserService userService;

    public SpotifyService(UserService userService) {
        this.userService = userService;
    }


    public String login() {
        SpotifyApi api = makeApi();
        URI uri = api.authorizationCodeUri()
                .scope("user-read-private, user-read-email, user-top-read")
                .show_dialog(true)
                .build()
                .execute();
        return uri.toString();
    }

    public SpotifyApi getAccessToken(String code) {
        SpotifyApi api = makeApi();
        AuthorizationCodeRequest authorizationCodeRequest = api.authorizationCode(code).build();

        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();
            String accessToken = authorizationCodeCredentials.getAccessToken();
            String refreshToken = authorizationCodeCredentials.getRefreshToken();
            LocalDateTime expires = LocalDateTime.now().plusSeconds(authorizationCodeCredentials.getExpiresIn());

            UserDto userDto = UserDto.builder()
                    .username(username)
                    .spotifyAccessToken(accessToken)
                    .spotifyRefreshToken(refreshToken)
                    .spotifyTokenExpiresAt(expires)
                    .build();
            log.info("Updating Spotify token for user: " + userDto);
            userService.updateSpotifyToken(username, userDto);

            api.setAccessToken(accessToken);
            api.setRefreshToken(refreshToken);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return api;
    }

    private SpotifyApi makeApi() {
        URI redirect = SpotifyHttpManager.makeUri(redirectUri);
        return new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRedirectUri(redirect)
                .build();
    }
}
