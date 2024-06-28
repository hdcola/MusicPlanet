package com.jac.fsd.musicplanet.repository;

import com.jac.fsd.musicplanet.model.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrackRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Track getTrackById(Long trackId) {
        String sql = "SELECT * FROM tracks WHERE track_id=?";
        try {
            return jdbcTemplate.queryForObject(sql, new TrackRowMapper(), trackId);
        } catch (Exception e) {
            return null;
        }
    }

    public Track getTrackByName(String trackName) {
        String sql = "SELECT * FROM tracks WHERE track_name=? LIMIT 1";

        return jdbcTemplate.queryForObject(sql, new TrackRowMapper(), trackName);
    }

    public List<Track> getTracksByAlbumId(Long albumId) {
        String sql = "SELECT * FROM tracks WHERE album_id=? LIMIT 10";

        try {
            return jdbcTemplate.query(sql, new TrackRowMapper(), albumId);
        } catch (Exception e) {
            return null;
        }
    }

    // TODO api db fill
    public Long saveTrack(Track track) {
        String sql = "INSERT INTO tracks(track_id, track_name, album_id, artist_id) VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(sql, track.getTrackId(), track.getTrackName(), track.getAlbumId(), track.getArtistId());

        return track.getTrackId();
    }

}
