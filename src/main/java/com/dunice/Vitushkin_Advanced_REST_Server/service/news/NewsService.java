package com.dunice.Vitushkin_Advanced_REST_Server.service.news;

import com.dunice.Vitushkin_Advanced_REST_Server.dto.news.GetNewsOutDto;
import com.dunice.Vitushkin_Advanced_REST_Server.dto.news.NewsDto;
import com.dunice.Vitushkin_Advanced_REST_Server.models.User;
import com.dunice.Vitushkin_Advanced_REST_Server.response.BaseSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.response.CreateNewsSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.response.CustomSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.response.PageableResponse;
import jakarta.annotation.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NewsService {
    public CreateNewsSuccessResponse createNews(User user, NewsDto request);

    public CustomSuccessResponse<PageableResponse<List<GetNewsOutDto>>> getPaginatedNews(
            Integer page,
            Integer perPage,
            @Nullable UUID userId);

    public PageableResponse<List<GetNewsOutDto>> getCertainNews(
            Integer page,
            Integer perPage,
            Optional<String> author,
            Optional<String> keywords,
            Optional<List<String>> tags);

    public BaseSuccessResponse updateNewsById(Long id, NewsDto request);

    public BaseSuccessResponse deleteNewsById(Long id);
}
