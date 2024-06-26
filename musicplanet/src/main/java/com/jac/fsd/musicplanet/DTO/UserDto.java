package com.jac.fsd.musicplanet.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
public class UserDto {
    private String username;
    private String password;
    private Boolean enabled;
    private String spotifyAccessToken;
    private String spotifyRefreshToken;
    private LocalDateTime spotifyTokenExpiresAt;
}
