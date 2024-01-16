package com.dunice.Vitushkin_Advanced_REST_Server.repository;

import java.util.Optional;

import com.dunice.Vitushkin_Advanced_REST_Server.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Boolean existsUserByEmail(String email);
}
