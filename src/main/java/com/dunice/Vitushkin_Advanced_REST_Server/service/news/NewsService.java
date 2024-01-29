package com.dunice.Vitushkin_Advanced_REST_Server.service.news;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.dunice.Vitushkin_Advanced_REST_Server.dto.news.GetNewsOutDto;
import com.dunice.Vitushkin_Advanced_REST_Server.dto.news.NewsDto;
import com.dunice.Vitushkin_Advanced_REST_Server.response.BaseSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.response.CreateNewsSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.response.CustomSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.response.PageableResponse;
import jakarta.annotation.Nullable;

public interface NewsService {
    CreateNewsSuccessResponse createNews(Principal principal, NewsDto request);

    CustomSuccessResponse<PageableResponse<List<GetNewsOutDto>>> getPaginatedNews(
            Integer page,
            Integer perPage,
            @Nullable UUID userId);
    PageableResponse<List<GetNewsOutDto>> getCertainNews(
            Integer page,
            Integer perPage,
            Optional<String> author,
            Optional<String> keywords,
            Optional<List<String>> tags);
    BaseSuccessResponse updateNewsById(Long id, NewsDto request);
    BaseSuccessResponse deleteNewsById(Long id);
}
