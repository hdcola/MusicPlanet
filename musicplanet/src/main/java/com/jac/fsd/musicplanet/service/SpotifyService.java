package com.jac.fsd.musicplanet.service;

import com.jac.fsd.musicplanet.DTO.UserDto;
import com.jac.fsd.musicplanet.entity.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;

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


    UserService userService;

    @Autowired
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

    public SpotifyApi getAccessToken(String username,String code) {
        SpotifyApi spotifyApi = makeApi();
        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();

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
            userService.updateSpotifyToken(username, userDto);

            spotifyApi.setAccessToken(accessToken);
            spotifyApi.setRefreshToken(refreshToken);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return spotifyApi;
    }

    private SpotifyApi makeApi(String username){
        User user = userService.getUser(username);
        SpotifyApi api = makeApi();
        LocalDateTime expires = user.getSpotifyTokenExpiresAt();
        if (expires.isBefore(LocalDateTime.now())) {
            api.setRefreshToken(user.getSpotifyRefreshToken());
            AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest = api.authorizationCodeRefresh().build();
            try {
                final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();
                String accessToken = authorizationCodeCredentials.getAccessToken();
                String refreshToken = authorizationCodeCredentials.getRefreshToken();
                LocalDateTime newExpires = LocalDateTime.now().plusSeconds(authorizationCodeCredentials.getExpiresIn());

                UserDto userDto = UserDto.builder()
                        .username(username)
                        .spotifyAccessToken(accessToken)
                        .spotifyRefreshToken(refreshToken)
                        .spotifyTokenExpiresAt(newExpires)
                        .build();
                userService.updateSpotifyToken(username, userDto);

                api.setAccessToken(accessToken);
                api.setRefreshToken(refreshToken);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        } else {
            api.setAccessToken(user.getSpotifyAccessToken());
            api.setRefreshToken(user.getSpotifyRefreshToken());
        }

        api.setAccessToken(user.getSpotifyAccessToken());
        api.setRefreshToken(user.getSpotifyRefreshToken());
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

    public PlaylistSimplified[] getPlaylists(String username) {
        SpotifyApi api = makeApi(username);
        try {
            Paging<PlaylistSimplified> playlists = api.getListOfCurrentUsersPlaylists().build().execute();
            log.info("Retrieved playlists for user: " + playlists.getTotal());
            return playlists.getItems();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return new PlaylistSimplified[0];
    }
}
