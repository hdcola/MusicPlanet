package com.jac.fsd.musicplanet.controller;

import com.jac.fsd.musicplanet.service.SpotifyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

@Controller
public class HomeController {
    SpotifyService spotifyService;

    public HomeController(SpotifyService spotifyService){
        this.spotifyService = spotifyService;
    }

    @GetMapping("/login")
    public String index(){
        return "login";
    }

    @GetMapping("/playlists")
    public String topArtists(Model model){
        PlaylistSimplified[] playlists = spotifyService.getPlaylists("danny");
        model.addAttribute("playlists", playlists);
        return "playlists";
    }
}
