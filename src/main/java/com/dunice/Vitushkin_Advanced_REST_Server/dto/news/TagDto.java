package com.dunice.Vitushkin_Advanced_REST_Server.dto.news;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagDto {
    private Integer id;
    private String title;
}
