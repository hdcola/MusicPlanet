package com.jac.fsd.musicplanet.repository;

import com.jac.fsd.musicplanet.model.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlbumRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Album getAlbumById(Long albumId) {
        String sql = "SELECT * FROM albums WHERE album_id=?";
        try {
            return jdbcTemplate.queryForObject(sql, new AlbumRowMapper(), albumId);
        } catch (Exception e) {
            return null;
        }

    }

    public Album getAlbumByName(String albumName) {
        String sql = "SELECT * FROM albums WHERE album_name=? LIMIT 1";
        try {
            return jdbcTemplate.queryForObject(sql, new AlbumRowMapper(), albumName);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Album> getAlbumsByArtistId(Long artistId) {
        String sql = "SELECT * FROM albums WHERE artist_id=?";
        try {
            return jdbcTemplate.query(sql, new AlbumRowMapper(), artistId);
        } catch (Exception e) {
            return null;
        }
    }

    // TODO api fill
    public Long saveAlbum(Album album) {
        String sql = "INSERT INTO albums(album_id, album_name, artist_id, year_of_release) VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(sql, album.getAlbumId(), album.getAlbumName(), album.getArtistId(), album.getYearOfRelease());

        return album.getAlbumId();
    }

}
