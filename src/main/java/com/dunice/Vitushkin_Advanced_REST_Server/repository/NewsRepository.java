package com.dunice.Vitushkin_Advanced_REST_Server.repository;

import com.dunice.Vitushkin_Advanced_REST_Server.models.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
