package com.dunice.Vitushkin_Advanced_REST_Server.mapper;

import java.util.List;

import com.dunice.Vitushkin_Advanced_REST_Server.dto.news.GetNewsOutDto;
import com.dunice.Vitushkin_Advanced_REST_Server.dto.news.NewsDto;
import com.dunice.Vitushkin_Advanced_REST_Server.models.News;
import com.dunice.Vitushkin_Advanced_REST_Server.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsMapper {
    private final TagMapper tagMapper;
    private final UserService userService;

    public News mapToEntity(NewsDto newsDto) {
        News newNews = News
                .builder()
                .description(newsDto.getDescription())
                .image(newsDto.getImage())
                .title(newsDto.getTitle())
                .build();

        newNews.setTags(
                newsDto.getTags()
                        .stream()
                        .map(tagMapper::maptoEntity)
                        .toList()
        );

        newNews.setUser(userService.getUserFromContext());
        return newNews;
    }

    public GetNewsOutDto mapToDto(News news) {
        return GetNewsOutDto.builder()
                .description(news.getDescription())
                .id(Math.toIntExact(news.getId()))
                .image(news.getImage())
                .tags(tagMapper.mapToListDto(news.getTags()))
                .userId(String.valueOf(news.getUser().getId()))
                .username(news.getUser().getUsername())
                .build();
    }

    public List<GetNewsOutDto> mapToListOfDto(List<News> newsList) {
        return newsList.stream().map(this::mapToDto).toList();
    }
}
