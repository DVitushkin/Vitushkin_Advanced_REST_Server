package com.dunice.Vitushkin_Advanced_REST_Server.dto.news;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class GetNewsOutDto {
    private String description;
    private Integer id;
    private String image;
    private List<TagDto> tags;
    private String userId;
    private String username;
}
