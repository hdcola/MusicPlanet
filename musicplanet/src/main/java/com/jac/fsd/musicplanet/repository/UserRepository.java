package com.jac.fsd.musicplanet.repository;

import com.jac.fsd.musicplanet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String>{
    User findByUsername(String username);
}
