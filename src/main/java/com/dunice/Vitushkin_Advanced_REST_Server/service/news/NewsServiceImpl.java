package com.dunice.Vitushkin_Advanced_REST_Server.service.news;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.dunice.Vitushkin_Advanced_REST_Server.dto.news.GetNewsOutDto;
import com.dunice.Vitushkin_Advanced_REST_Server.dto.news.NewsDto;
import com.dunice.Vitushkin_Advanced_REST_Server.exception.ErrorsMsg;
import com.dunice.Vitushkin_Advanced_REST_Server.mapper.NewsMapper;
import com.dunice.Vitushkin_Advanced_REST_Server.mapper.TagMapper;
import com.dunice.Vitushkin_Advanced_REST_Server.models.News;
import com.dunice.Vitushkin_Advanced_REST_Server.models.Tag;
import com.dunice.Vitushkin_Advanced_REST_Server.models.User;
import com.dunice.Vitushkin_Advanced_REST_Server.repository.NewsDao;
import com.dunice.Vitushkin_Advanced_REST_Server.repository.NewsRepository;
import com.dunice.Vitushkin_Advanced_REST_Server.repository.TagRepository;
import com.dunice.Vitushkin_Advanced_REST_Server.repository.UserRepository;
import com.dunice.Vitushkin_Advanced_REST_Server.response.BaseSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.response.CreateNewsSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.response.CustomSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.response.PageableResponse;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final NewsDao newsDao;
    private final NewsMapper newsMapper;
    private final TagMapper tagMapper;

    @Override
    public CreateNewsSuccessResponse createNews(Principal principal, NewsDto request) {
        Optional<User> user = userRepository.findById(UUID.fromString(principal.getName()));
        if (user.isEmpty()) {
            throw new EntityNotFoundException(ErrorsMsg.USER_NOT_FOUND);
        }
        User userEntity = user.get();

        News newNews = newsMapper.mapToEntity(request);
        newNews.setUser(userEntity);
        newsRepository.save(newNews);
        return CreateNewsSuccessResponse.ok(newNews.getId());
    }

    @Override
    public CustomSuccessResponse<PageableResponse<List<GetNewsOutDto>>> getPaginatedNews(
            Integer page,
            Integer perPage,
            @Nullable UUID userId) {
        Page<News> news;
        if (userId == null) {
            news = newsRepository.findAll(PageRequest.of(page - 1, perPage));
        } else {
            news = newsRepository.findAllByUserId(userId, PageRequest.of(page - 1, perPage));
        }
        List<GetNewsOutDto> content = newsMapper.mapToListOfDto(news.getContent());

        return CustomSuccessResponse.withData(PageableResponse.<List<GetNewsOutDto>>builder()
                .content(content)
                .numberOfElements(news.getTotalElements())
                .build()
        );
    }

    @Override
    public PageableResponse<List<GetNewsOutDto>> getCertainNews(
            Integer page,
            Integer perPage,
            Optional<String> author,
            Optional<String> keywords,
            Optional<List<String>> tags) {
        Page<News> certainNews = newsDao.findAllByParameters(author, keywords, tags, PageRequest.of(page - 1, perPage));

        List<GetNewsOutDto> content = newsMapper.mapToListOfDto(certainNews.getContent());
        return  PageableResponse.<List<GetNewsOutDto>>builder()
                .content(content)
                .numberOfElements(certainNews.getTotalElements())
                .build();
    }

    @Override
    @Transactional
    public BaseSuccessResponse updateNewsById(Long id, NewsDto request) {
        News newsToUpdate = newsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorsMsg.NEWS_NOT_FOUND));

        List<Tag> removedTags = newsToUpdate
                .getTags()
                .stream()
                .filter(tag -> !request.getTags().contains(tag.getTitle()))
                .toList();

        newsToUpdate.setDescription(request.getDescription())
                .setImage(request.getImage())
                .setTitle(request.getTitle())
                .setTags(tagMapper.mapToListEntity(request.getTags()));

        removedTags
                .stream()
                .filter(tag -> newsRepository.countAllByTags(tag) == 0)
                .forEach(tagRepository::delete);



        return BaseSuccessResponse.ok();
    }

    @Override
    public BaseSuccessResponse deleteNewsById(Long id) {
        if (!newsRepository.existsById(id)) {
            throw new EntityNotFoundException(ErrorsMsg.NEWS_NOT_FOUND);
        }

        newsRepository.deleteById(id);
        return BaseSuccessResponse.ok();
    }
}
