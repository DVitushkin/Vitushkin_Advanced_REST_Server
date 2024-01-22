package com.dunice.Vitushkin_Advanced_REST_Server.mapper;

import java.util.List;

import com.dunice.Vitushkin_Advanced_REST_Server.dto.news.TagDto;
import com.dunice.Vitushkin_Advanced_REST_Server.models.Tag;
import com.dunice.Vitushkin_Advanced_REST_Server.repository.TagRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TagMapper {
    private final TagRepository tagRepository;

    public Tag maptoEntity(String title) {
        Tag tag = tagRepository.findByTitle(title);
        if (tag != null) {
            return tag;
        }

        tag = new Tag();
        tag.setTitle(title);
        return tag;
    };

    public List<Tag> mapToListEntity(List<String> tags) {
        return  tags.stream()
                .map(this::maptoEntity)
                .toList();
    }

    public TagDto mapToDto(Tag tag) {
        return TagDto.builder()
                .id(Math.toIntExact(tag.getId()))
                .title(tag.getTitle())
                .build();
    }

    public List<TagDto> mapToListDto(List<Tag> tags) {
        return tags.stream()
                .map(this::mapToDto)
                .toList();
    }
}
