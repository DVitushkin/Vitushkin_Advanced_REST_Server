package com.dunice.Vitushkin_Advanced_REST_Server.repository;

import java.util.Optional;
import java.util.UUID;

import com.dunice.Vitushkin_Advanced_REST_Server.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT count (u) > 0 FROM User u WHERE u.email = :email")
    Boolean existsUserByEmail(@Param("email") String email);
}
