package com.jac.fsd.musicplanet.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AlbumDTO {
    @JsonProperty("idAlbum")
    private Long albumId;

    @JsonProperty("strAlbum")
    private String albumName;

    @JsonProperty("idArtist")
    private Long artistId;

    @JsonProperty("intYearReleased")
    private String yearOfRelease;
}
