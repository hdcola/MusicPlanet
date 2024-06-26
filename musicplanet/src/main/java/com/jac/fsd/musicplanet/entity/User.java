package com.jac.fsd.musicplanet.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    private String username;
    private String password;
    private Boolean enabled;
    private String spotifyAccessToken;
    private String spotifyRefreshToken;
    private LocalDateTime spotifyTokenExpiresAt;
}
