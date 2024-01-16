package com.dunice.Vitushkin_Advanced_REST_Server.repository;

import com.dunice.Vitushkin_Advanced_REST_Server.models.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<LogEntity, Long> {
}
