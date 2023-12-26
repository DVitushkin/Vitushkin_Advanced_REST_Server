package com.dunice.Vitushkin_Advanced_REST_Server.repository;

import com.dunice.Vitushkin_Advanced_REST_Server.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
