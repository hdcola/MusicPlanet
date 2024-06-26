package com.jac.fsd.musicplanet.service;

import com.jac.fsd.musicplanet.DTO.UserDto;
import com.jac.fsd.musicplanet.entity.User;
import com.jac.fsd.musicplanet.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User updateSpotifyToken(String username, UserDto userDto) {
        User user = userRepository.findByUsername(username);
        user.setSpotifyAccessToken(userDto.getSpotifyAccessToken());
        user.setSpotifyRefreshToken(userDto.getSpotifyRefreshToken());
        user.setSpotifyTokenExpiresAt(userDto.getSpotifyTokenExpiresAt());
        return userRepository.save(user);
    }
}
