package com.dunice.Vitushkin_Advanced_REST_Server.controller;

import java.util.List;
import java.util.UUID;

import com.dunice.Vitushkin_Advanced_REST_Server.dto.news.GetNewsOutDto;
import com.dunice.Vitushkin_Advanced_REST_Server.dto.news.NewsDto;
import com.dunice.Vitushkin_Advanced_REST_Server.exception.ErrorsMsg;
import com.dunice.Vitushkin_Advanced_REST_Server.response.CreateNewsSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.response.CustomSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.response.PageableResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.service.NewsService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @PostMapping
    public ResponseEntity<CreateNewsSuccessResponse> createNews(@Validated @RequestBody NewsDto request) {
        return ResponseEntity.ok(newsService.createNews(request));
    }

    @GetMapping
    @Validated
    public ResponseEntity<CustomSuccessResponse<PageableResponse<List<GetNewsOutDto>>>> getPaginatedNews(
            @RequestParam("page")
            @Min(value = 1, message = ErrorsMsg.PAGE_SIZE_NOT_VALID)
            Integer page,
            @RequestParam("perPage")
            @Min(value = 1, message = ErrorsMsg.PER_PAGE_MIN_NOT_VALID)
            @Max(value = 100, message = ErrorsMsg.PER_PAGE_MAX_NOT_VALID)
            Integer perPage
    ) {
        return ResponseEntity.ok(newsService.getPaginatedNews(page, perPage, null));
    }

    @GetMapping("/user/{userId}")
    @Validated
    public ResponseEntity<CustomSuccessResponse<PageableResponse<List<GetNewsOutDto>>>> getUserPaginatedNews(
            @PathVariable("userId")
            @NotNull(message = ErrorsMsg.ID_MUST_BE_POSITIVE)
            UUID userId,
            @RequestParam("page")
            @Min(value = 1, message = ErrorsMsg.PAGE_SIZE_NOT_VALID)
            Integer page,
            @RequestParam("perPage")
            @Min(value = 1, message = ErrorsMsg.PER_PAGE_MIN_NOT_VALID)
            @Max(value = 100, message = ErrorsMsg.PER_PAGE_MAX_NOT_VALID)
            Integer perPage
    ) {
        return ResponseEntity.ok(newsService.getPaginatedNews(page, perPage, userId));
    }
}
