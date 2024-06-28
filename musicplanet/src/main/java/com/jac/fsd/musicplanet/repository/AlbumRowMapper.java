package com.jac.fsd.musicplanet.repository;

import com.jac.fsd.musicplanet.model.Album;
import com.jac.fsd.musicplanet.model.Track;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AlbumRowMapper implements RowMapper<Album> {
    @Override
    public Album mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Album.builder().albumId(rs.getLong("album_id"))
                .albumName(rs.getString("album_name"))
                .artistId(rs.getLong("artist_id"))
                .yearOfRelease(rs.getInt("year_of_release"))
                .build();
    }
}
