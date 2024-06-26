package com.jac.fsd.musicplanet.controller;

import com.jac.fsd.musicplanet.service.SpotifyService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.SpotifyApi;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class SpotifyController {
    SpotifyService spotifyService;

    @Autowired
    public SpotifyController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }


    @GetMapping("/spotify/login")
    @ResponseBody
    public String SpotifyLogin() {
        return spotifyService.login();
    }

    @GetMapping("/spotify/callback")
    public String SpotifyCallback(@RequestParam("code") String userCode, HttpServletResponse response) throws IOException {
        SpotifyApi spotifyApi = spotifyService.getAccessToken("danny", userCode);

        response.sendRedirect("/playlists");

        return spotifyApi.getAccessToken();
    }

}
