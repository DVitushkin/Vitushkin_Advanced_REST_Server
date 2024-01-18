package com.dunice.Vitushkin_Advanced_REST_Server.service;

import com.dunice.Vitushkin_Advanced_REST_Server.dto.news.NewsDto;
import com.dunice.Vitushkin_Advanced_REST_Server.mapper.NewsMapper;
import com.dunice.Vitushkin_Advanced_REST_Server.repository.NewsRepository;
import com.dunice.Vitushkin_Advanced_REST_Server.response.CreateNewsSuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.dunice.Vitushkin_Advanced_REST_Server.models.News;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    public CreateNewsSuccessResponse createUser(NewsDto request) {
        News newNews = newsMapper.mapToEntity(request);
        newNews.getTags().forEach(tag -> tag.setNews(List.of(newNews)));
        newsRepository.save(newNews);
        return CreateNewsSuccessResponse.ok(newNews.getId());
    }
}
