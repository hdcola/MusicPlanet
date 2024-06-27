package com.jac.fsd.musicplanet.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class TrackListDTO {

    @JsonProperty("track")
    private List<TrackDTO> trackDTOs;
}
