package com.dunice.Vitushkin_Advanced_REST_Server.service.news;

import com.dunice.Vitushkin_Advanced_REST_Server.dto.news.NewsDto;
import com.dunice.Vitushkin_Advanced_REST_Server.mapper.NewsMapper;
import com.dunice.Vitushkin_Advanced_REST_Server.mapper.TagMapper;
import com.dunice.Vitushkin_Advanced_REST_Server.models.News;
import com.dunice.Vitushkin_Advanced_REST_Server.models.Tag;
import com.dunice.Vitushkin_Advanced_REST_Server.models.User;
import com.dunice.Vitushkin_Advanced_REST_Server.repository.NewsDao;
import com.dunice.Vitushkin_Advanced_REST_Server.repository.NewsRepository;
import com.dunice.Vitushkin_Advanced_REST_Server.repository.TagRepository;
import com.dunice.Vitushkin_Advanced_REST_Server.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.springframework.data.domain.Page;

@ExtendWith(MockitoExtension.class)
class NewsServiceImplTest {
    @Mock
    private NewsRepository newsRepository;

    @Mock
    private static UserRepository userRepository;

    @Mock
    private static TagRepository tagRepository;

    @Mock
    private static NewsMapper newsMapper;

    @Mock
    private static TagMapper tagMapper;

    @Mock
    private static NewsDao newsDao;

    @InjectMocks
    private NewsServiceImpl newsService;

    private static Principal mockPrincipal;
    private static News newNews;
    private static List<News> listOfNews = new ArrayList<>();
    private static NewsDto newsDto;
    private static User user;
    private static final UUID correctUUID = UUID.randomUUID();
    private static Long newsId;

    private static String getRandomString(Integer stringLength) {
        Integer leftLimit = 48;
        Integer rightLimit = 122;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(stringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private static void generateListOfNews(Integer listLength) {
        if (!listOfNews.isEmpty()) {
            listOfNews.clear();
        }
        Random random = new Random();

        for (int i = 0; i < listLength; i++) {
            News n = News
                    .builder()
                    .id(random.nextLong())
                    .description(getRandomString(7) + " description")
                    .image(getRandomString(3) + "/to/image")
                    .title(getRandomString(6) + " Title")
                    .build();

            Integer limitOfTags = random.nextInt(1, 5);
            List<Tag> tags = new ArrayList<>();
            for (int j = 0; j < limitOfTags; j++) {
                Tag tag = new Tag();
                tag.setId(random.nextLong());
                tag.setTitle("tag" + getRandomString(5));
                tags.add(j, tag);
            }
            n.setTags(tags);
            listOfNews.add(i, n);
        }
    }

    @BeforeAll
    public static void setup() {
        newsId = new Random().nextLong(1, 13);
        user = User
                .builder()
                .avatar("path/to/avatar")
                .email("defaultEmail@mail.ru")
                .name("Default Name")
                .password("encodedPassword")
                .role("user")
                .build();

        newsDto = NewsDto.builder()
                .description("Default description")
                .image("path/to/news/image")
                .tags(List.of("tag1", "tag2"))
                .title("Default title")
                .build();

        newNews = News
                .builder()
                .id(newsId)
                .description(newsDto.getDescription())
                .image(newsDto.getImage())
                .title(newsDto.getTitle())
                .build();

        newNews.setTags(
                newsDto.getTags().stream().map(title -> {
                    Tag tag = new Tag();
                    tag.setTitle(title);
                    return tag;
                }).toList()
        );

        mockPrincipal = mock(Principal.class);
        when(mockPrincipal.getName())
                .thenReturn(String.valueOf(correctUUID));
    }

    @Test
    public void shouldReturnSuccessCreateNews() {
        when(userRepository.findById(correctUUID))
                .thenReturn(Optional.ofNullable(user));

        when(newsMapper.mapToEntity(newsDto))
                .thenReturn(newNews);

        when(newsRepository.save(newNews))
                .thenReturn(newNews);

        var result = newsService.createNews(mockPrincipal, newsDto);
        assertEquals(true, result.getSuccess());
        assertEquals(1, result.getStatusCode());
        assertEquals(newsId, result.getId());
    }

    @Test
    public void shouldReturnAllPaginatedNews() {
        var pageRequest = PageRequest.of(1, 3);

        Integer sizeOfListOfNews = 13;
        generateListOfNews(sizeOfListOfNews);

        Page<News> AllPaginatedNewsMock = new PageImpl<>(new ArrayList<>(), pageRequest, sizeOfListOfNews);
        when(newsRepository.findAll(pageRequest))
                .thenReturn(AllPaginatedNewsMock);

        var result = newsService.getPaginatedNews(pageRequest.getPageNumber() + 1, pageRequest.getPageSize(), null);
        assertEquals(true, result.getSuccess());
        assertEquals(1, result.getStatusCode());
        assertEquals(sizeOfListOfNews, result.getData().getNumberOfElements().intValue());
    }

    @Test
    public void shouldReturnPaginatedNewsByUser() {
        var pageRequest = PageRequest.of(1, 3);

        Integer sizeOfListOfNews = 13;
        generateListOfNews(sizeOfListOfNews);

        Page<News> PaginatedNewsByUserMock = new PageImpl<>(new ArrayList<>(), pageRequest, sizeOfListOfNews);
        when(newsRepository.findAll(pageRequest))
                .thenReturn(PaginatedNewsByUserMock);

        var result = newsService.getPaginatedNews(pageRequest.getPageNumber() + 1, pageRequest.getPageSize(), null);
        assertEquals(true, result.getSuccess());
        assertEquals(1, result.getStatusCode());
        assertEquals(sizeOfListOfNews, result.getData().getNumberOfElements().intValue());
    }

    @Test
    public void shouldReturnPaginatedNewsByParams() {
        var pageRequest = PageRequest.of(1, 3);
        Optional<String> author = Optional.of("Marked T");
        Optional<String> keywords = Optional.of("rked Desc");
        Optional<List<String>> tags = Optional.of(List.of("tag1"));

        Integer sizeOfListOfNews = 13;
        generateListOfNews(sizeOfListOfNews);

        Page<News> certainNewsMock = new PageImpl<>(new ArrayList<>(), pageRequest, sizeOfListOfNews);
        when(newsDao.findAllByParameters(author, keywords, tags, pageRequest))
                .thenReturn(certainNewsMock);

        var result = newsService.getCertainNews(pageRequest.getPageNumber() + 1, pageRequest.getPageSize(), author, keywords, tags);
        assertEquals(sizeOfListOfNews, result.getNumberOfElements().intValue());
    }

    @Test
    public void shouldReturnSuccessUpdate() {
        when(newsRepository.findById(newsId))
                .thenReturn(Optional.ofNullable(newNews));

        var result = newsService.updateNewsById(newsId, newsDto);
        assertEquals(true, result.getSuccess());
        assertEquals(1, result.getStatusCode());
    }

    @Test
    public void shouldReturnEntityNotFound() {
        when(newsRepository.findById(newsId))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> newsService.updateNewsById(newsId, newsDto));
    }

    @Test
    public void shouldReturnSuccessDelete() {
        when(newsRepository.existsById(newsId))
                .thenReturn(true);

        var result = newsService.deleteNewsById(newsId);
        assertEquals(true, result.getSuccess());
        assertEquals(1, result.getStatusCode());
    }

    @Test
    public void shouldReturnErrorDelete() {
        when(newsRepository.existsById(newsId))
                .thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> newsService.deleteNewsById(newsId));
    }
}