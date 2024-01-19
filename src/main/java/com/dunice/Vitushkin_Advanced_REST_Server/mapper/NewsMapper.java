package com.dunice.Vitushkin_Advanced_REST_Server.mapper;

import com.dunice.Vitushkin_Advanced_REST_Server.dto.news.NewsDto;
import com.dunice.Vitushkin_Advanced_REST_Server.models.News;
import com.dunice.Vitushkin_Advanced_REST_Server.models.Tag;
import com.dunice.Vitushkin_Advanced_REST_Server.repository.TagRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = TagRepository.class)
public abstract class NewsMapper {
    @Autowired
    protected TagRepository tagRepository;

    @Mapping(target = "tags", source = "tags")
    public abstract News mapToEntity(NewsDto newsDto);

    protected Tag stringToTag(String title) {

        Tag tag = tagRepository.findByTitle(title);
        if (tag != null) {
            return tag;
        }
        tag = new Tag();
        tag.setTitle(title);
        return tag;
    };

    protected List<Tag> setNews(List<String> l) {
        return l.stream().map(this::stringToTag).toList();
    }
}
