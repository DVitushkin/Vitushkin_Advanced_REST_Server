package com.dunice.Vitushkin_Advanced_REST_Server.mapper;

import com.dunice.Vitushkin_Advanced_REST_Server.dto.news.NewsDto;
import com.dunice.Vitushkin_Advanced_REST_Server.models.News;
import com.dunice.Vitushkin_Advanced_REST_Server.models.Tag;
import com.dunice.Vitushkin_Advanced_REST_Server.repository.TagRepository;
import com.dunice.Vitushkin_Advanced_REST_Server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsMapper {
    private final TagRepository tagRepository;
    private final UserService userService;

    private Tag stringToTag(String title) {

        Tag tag = tagRepository.findByTitle(title);
        if (tag != null) {
            return tag;
        }
        tag = new Tag();
        tag.setTitle(title);
        return tag;
    };
    public News mapToEntity(NewsDto newsDto) {
        News newNews = News
                .builder()
                .description(newsDto.getDescription())
                .image(newsDto.getImage())
                .title(newsDto.getTitle())
                .build();

        newNews.setTags(
                newsDto
                        .getTags()
                        .stream()
                        .map(this::stringToTag)
                        .toList()
        );

        newNews.setUser(userService.getUserFromContext());
        return newNews;
    }
}
