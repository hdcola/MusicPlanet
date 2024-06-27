package com.jac.fsd.musicplanet.service;

import com.jac.fsd.musicplanet.adapter.AudiodbAdapter;
import com.jac.fsd.musicplanet.model.Album;
import com.jac.fsd.musicplanet.model.Artist;
import com.jac.fsd.musicplanet.model.Biography;
import com.jac.fsd.musicplanet.repository.ArtistRepository;
import com.jac.fsd.musicplanet.model.Track;
import com.jac.fsd.musicplanet.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    @Autowired
    private AudiodbAdapter adapter;

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private ArtistRepository artistRepository;

    public List<Album> getDiscography(String artistName) {
        var discographyDTO = adapter.getDiscography(artistName);
        // map each albumDTO to an Album object and collect the mapped Album objects into a list
        return discographyDTO.getAlbums().stream()
                .map(albumDTO -> Album.builder()
                        .albumName(albumDTO.getAlbumName())
                        .yearOfRelease(Integer.parseInt(albumDTO.getYearOfRelease()))
                        .build())
                .collect(Collectors.toList());
    }

    public List<Track> getTracksByAlbumId(Long albumId) {
        List<Track> tracks = trackRepository.getTracksByAlbumId(albumId);

        // check for empty array return
        if (tracks == null || tracks.isEmpty()) {
            var trackListDTO = adapter.getTracksByAlbumId(albumId);

            return trackListDTO.getTrackDTOs().stream()
                    .map(trackDTO -> Track.builder()
                            .trackId(trackDTO.getTrackId())
                            .trackName(trackDTO.getTrackName())
                            .albumId(trackDTO.getAlbumId())
                            .artistId(trackDTO.getArtistId())
                            .build())
                    .collect(Collectors.toList());
        } else {
            return tracks;
        }
    }


    public Track getTrackByTrackId(Long trackID) {
        Track track = trackRepository.getTrackById(trackID);

        if (track != null) {
            return track;
        } else {
            // audioDb returns a single array element that we must unpack
            var trackDTO = adapter.getTrackByTrackId(trackID).getTrackDTOs().get(0);

            return Track.builder()
                    .trackId(trackDTO.getTrackId())
                    .trackName(trackDTO.getTrackName())
                    .albumId(trackDTO.getAlbumId())
                    .artistId(trackDTO.getArtistId())
                    .build();
        }
    }

    public Artist getArtistByName(String theArtistName) {
        var theArtistId = artistRepository.getArtistId(theArtistName);
        Artist artistObj =  Artist.builder()
                .artistName(theArtistName)
                .artistId(theArtistId)
                .build();
        return artistObj;
    }

    public Biography getBiography(int artistId) {
        var artistDetailsDTO = adapter.getBiography(artistId);
        // since there is only one artist in our List, we access it with get(0)
        String biographyStr = artistDetailsDTO.getArtistDetails().get(0).getBiography();
        Biography biographyObj = Biography.builder()
                .biography(biographyStr).build();

        return biographyObj;
    }
}
