package com.dunice.Vitushkin_Advanced_REST_Server.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.dunice.Vitushkin_Advanced_REST_Server.models.News;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NewsDao {
    private final EntityManager entityManager;

    public Page<News> findAllByParameters(
            Optional<String> author,
            Optional<String> keywords,
            Optional<List<String>> tags,
            Pageable pageable
    ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<News> newsQuery = cb.createQuery(News.class);
        Root<News> newsRoot = newsQuery.from(News.class);

        List<Predicate> predicates = new ArrayList<>();
        author.ifPresent(string -> predicates.add(cb.equal(newsRoot.get("user").get("name"), string)));
        keywords.ifPresent(string -> predicates.add(cb.like(newsRoot.get("description"), "%" + keywords.orElse(null) + "%")));
        tags.ifPresent(strings -> predicates.add(newsRoot.get("tags").get("title").in(strings)));

        newsQuery.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<News> query = entityManager.createQuery(newsQuery);
        List<News> content = query.getResultList();

        List<News> pageOfContent = content.subList(
                pageable.getPageNumber() * pageable.getPageSize(),
                pageable.getPageNumber() * pageable.getPageSize() + pageable.getPageSize());
        var totalElements = content.size();
        return new PageImpl<>(pageOfContent, pageable, totalElements);
    }
}
