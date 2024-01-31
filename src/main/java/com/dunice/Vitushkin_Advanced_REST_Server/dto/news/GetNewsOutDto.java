package com.dunice.Vitushkin_Advanced_REST_Server.dto.news;

import java.util.List;

import lombok.Builder;
import lombok.Data;

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
