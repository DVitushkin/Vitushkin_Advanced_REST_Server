package com.dunice.Vitushkin_Advanced_REST_Server.repository;

import com.dunice.Vitushkin_Advanced_REST_Server.models.Tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("SELECT t FROM Tag t WHERE t.title = :title")
    Tag findByTitle(@Param("title") String title);
}
