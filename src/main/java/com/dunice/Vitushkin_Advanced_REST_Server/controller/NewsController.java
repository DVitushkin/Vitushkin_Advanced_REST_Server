package com.dunice.Vitushkin_Advanced_REST_Server.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.dunice.Vitushkin_Advanced_REST_Server.dto.news.GetNewsOutDto;
import com.dunice.Vitushkin_Advanced_REST_Server.dto.news.NewsDto;
import com.dunice.Vitushkin_Advanced_REST_Server.exception.ErrorsMsg;
import com.dunice.Vitushkin_Advanced_REST_Server.response.BaseSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.response.CreateNewsSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.response.CustomSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.response.PageableResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.service.NewsService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/news")
@RequiredArgsConstructor
@Validated
public class NewsController {
    private final NewsService newsService;

    @PostMapping
    public ResponseEntity<CreateNewsSuccessResponse> createNews(@Validated @RequestBody NewsDto request) {
        return ResponseEntity.ok(newsService.createNews(request));
    }

    @GetMapping
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

    @GetMapping("/find")
    public ResponseEntity<PageableResponse<List<GetNewsOutDto>>> getCertainNews(
            @RequestParam("page")
            @Min(value = 1, message = ErrorsMsg.PAGE_SIZE_NOT_VALID)
            Integer page,
            @RequestParam("perPage")
            @Min(value = 1, message = ErrorsMsg.PER_PAGE_MIN_NOT_VALID)
            @Max(value = 100, message = ErrorsMsg.PER_PAGE_MAX_NOT_VALID)
            Integer perPage,
            @RequestParam("author") Optional<String> author,
            @RequestParam("keywords") Optional<String> keywords,
            @RequestParam("tags") Optional<List<String>> tags
            ) {
        return ResponseEntity.ok(newsService.getCertainNews(page, perPage, author, keywords, tags));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseSuccessResponse> updateNewsById(
            @PathVariable("id")
            @Positive(message = ErrorsMsg.ID_MUST_BE_POSITIVE)
            Long id,
            @Validated
            @RequestBody
            NewsDto request
    ) {
        return ResponseEntity.ok(newsService.updateNewsById(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseSuccessResponse> deleteNewsById(@Positive(message = ErrorsMsg.ID_MUST_BE_POSITIVE) @PathVariable("id") Long id) {
        return ResponseEntity.ok(newsService.deleteNewsById(id));
    }
}
