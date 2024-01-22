package com.dunice.Vitushkin_Advanced_REST_Server.service;

import java.util.List;

import com.dunice.Vitushkin_Advanced_REST_Server.dto.news.GetNewsOutDto;
import com.dunice.Vitushkin_Advanced_REST_Server.dto.news.NewsDto;
import com.dunice.Vitushkin_Advanced_REST_Server.mapper.NewsMapper;
import com.dunice.Vitushkin_Advanced_REST_Server.models.News;
import com.dunice.Vitushkin_Advanced_REST_Server.repository.NewsRepository;
import com.dunice.Vitushkin_Advanced_REST_Server.response.CreateNewsSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.response.CustomSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.response.PageableResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


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

    public CustomSuccessResponse<PageableResponse<List<GetNewsOutDto>>> getPaginatedNews(Integer page, Integer perPage) {
        Page<News> news = newsRepository.findAll(PageRequest.of(page - 1, perPage));
        List<GetNewsOutDto> content = newsMapper.mapToListOfDto(news.getContent());

        return CustomSuccessResponse.withData(PageableResponse.<List<GetNewsOutDto>>builder()
                .content(content)
                .numberOfElements(news.getNumberOfElements())
                .build()
        );
    }
}
