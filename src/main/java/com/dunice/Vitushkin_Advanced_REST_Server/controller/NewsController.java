package com.dunice.Vitushkin_Advanced_REST_Server.controller;

import com.dunice.Vitushkin_Advanced_REST_Server.dto.news.NewsDto;
import com.dunice.Vitushkin_Advanced_REST_Server.response.CreateNewsSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @PostMapping
    public ResponseEntity<CreateNewsSuccessResponse> createNews(
            @Validated @RequestBody NewsDto request
    ) {
        return ResponseEntity.ok(newsService.createUser(request));
    }
}