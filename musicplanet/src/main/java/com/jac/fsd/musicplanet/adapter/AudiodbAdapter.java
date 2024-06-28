package com.jac.fsd.musicplanet.adapter;

import com.jac.fsd.musicplanet.DTO.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class AudiodbAdapter {

    @Value("${audiodb.url}")
    private String apiUrl;
    @Value("${audiodb.key}")
    private String apiKey;


    public DiscographyDTO getDiscography(String artistName) {

        RestTemplate restTemplate = new RestTemplate();

        final String route = apiUrl.concat("{key}/discography.php?s={name}");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("key", apiKey);
        parameters.put("name", artistName);

        DiscographyDTO discographyDTO = restTemplate.getForObject(route, DiscographyDTO.class, parameters);
        return discographyDTO;
    }

    public ArtistDetailsDTO getBiography(int artistId) {

        RestTemplate restTemplate = new RestTemplate();

        final String route = apiUrl.concat("{key}/artist.php?i={id}");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("key", apiKey);
        parameters.put("id", String.valueOf(artistId));

        ArtistDetailsDTO artistDetailsDTO = restTemplate.getForObject(route, ArtistDetailsDTO.class, parameters);
        return artistDetailsDTO;
    }


    public TrackListDTO getTracksByAlbumId(Long albumId) {
        RestTemplate restTemplate = new RestTemplate();

        final String route = apiUrl.concat("{key}/track.php?m={id}");

        // turn the Long into a String to make parameters setup cleaner
        String strId = Long.toString(albumId);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("key", apiKey);
        parameters.put("id", strId);

        TrackListDTO trackDTOList = restTemplate.getForObject(route, TrackListDTO.class, parameters);
        return trackDTOList;
    }

    // returning a TrackListDTO because the data is returned in an array of a single element
    public TrackListDTO getTrackByTrackId(Long trackId) {
        RestTemplate restTemplate = new RestTemplate();

        final String route = apiUrl.concat("{key}/track.php?h={id}");

        String strId = Long.toString(trackId);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("key", apiKey);
        parameters.put("id", strId);

        TrackListDTO trackDTOList = restTemplate.getForObject(route, TrackListDTO.class, parameters);
        return trackDTOList;
    }

    public AlbumListDTO getAlbumById(Long albumId) {
        RestTemplate restTemplate = new RestTemplate();

        final String route = apiUrl.concat("{key}/album.php?m={id}");

        String strId = Long.toString(albumId);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("key", apiKey);
        parameters.put("id", strId);

        AlbumListDTO albumListDTO = restTemplate.getForObject(route, AlbumListDTO.class, parameters);
        return albumListDTO;
    }

    public AlbumListDTO getAlbumsByArtistId(Long artistId) {
        RestTemplate restTemplate = new RestTemplate();

        final String route = apiUrl.concat("{key}/album.php?i={id}");

        String strId = Long.toString(artistId);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("key", apiKey);
        parameters.put("id", strId);

        AlbumListDTO albumListDTO = restTemplate.getForObject(route, AlbumListDTO.class, parameters);
        return albumListDTO;
    }
}
