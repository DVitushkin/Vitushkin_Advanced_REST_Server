package com.dunice.Vitushkin_Advanced_REST_Server.repository;

import java.util.List;
import java.util.UUID;

import com.dunice.Vitushkin_Advanced_REST_Server.models.News;

import com.dunice.Vitushkin_Advanced_REST_Server.models.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface NewsRepository extends JpaRepository<News, Long>, JpaSpecificationExecutor<News> {
    @Query("select n from News n join fetch n.tags where n.user.id = :userId")
    Page<News> findAllByUserId(UUID userId, PageRequest of);

    long countAllByTags(Tag tag);

    Page<News> findAll(Specification<News> spec, Pageable pageable);
}
