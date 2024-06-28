package com.jac.fsd.musicplanet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Album {
    private Long albumId;
    private String albumName;
    private Long artistId;
    private int yearOfRelease;
}
