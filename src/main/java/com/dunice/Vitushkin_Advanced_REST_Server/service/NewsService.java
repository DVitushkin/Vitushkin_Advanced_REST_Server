package com.dunice.Vitushkin_Advanced_REST_Server.service;

import com.dunice.Vitushkin_Advanced_REST_Server.dto.news.NewsDto;
import com.dunice.Vitushkin_Advanced_REST_Server.mapper.NewsMapper;
import com.dunice.Vitushkin_Advanced_REST_Server.repository.NewsRepository;
import com.dunice.Vitushkin_Advanced_REST_Server.response.CreateNewsSuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.dunice.Vitushkin_Advanced_REST_Server.models.News;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    public CreateNewsSuccessResponse createNews(NewsDto request) {
        News newNews = newsMapper.mapToEntity(request);
        newsRepository.save(newNews);
        return CreateNewsSuccessResponse.ok(newNews.getId());
    }
}
