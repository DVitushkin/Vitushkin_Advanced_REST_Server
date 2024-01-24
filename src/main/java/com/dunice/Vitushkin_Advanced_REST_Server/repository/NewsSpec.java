package com.dunice.Vitushkin_Advanced_REST_Server.repository;

import java.util.List;
import java.util.Optional;

import com.dunice.Vitushkin_Advanced_REST_Server.models.News;
import lombok.RequiredArgsConstructor;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
@RequiredArgsConstructor
public class NewsSpec {
    public static Specification<News> authorEqualSpec(Optional<String> author) {
        if (author.isPresent()) {
            return ((root, query, cb) -> cb.equal(root.get("user").get("name"), author.get()));
        }
        return where(null);
    }

    public static Specification<News> descriptionInSpec(Optional<String> keywords) {
        if (keywords.isPresent()) {
            return ((root, query, cb) -> cb.like(root.get("description"), "%" + keywords.get() + "%"));
        }
        return where(null);
    }

    public static Specification<News> tagsInSpec(Optional<List<String>> tags) {
        if (tags.isPresent()) {
            return ((root, query, cb) -> cb.in(root.get("tags").get("title")).value(tags.get()));
        }
        return where(null);
    }
}
