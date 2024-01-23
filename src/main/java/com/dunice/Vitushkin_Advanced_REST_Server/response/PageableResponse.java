package com.dunice.Vitushkin_Advanced_REST_Server.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PageableResponse<T> {
    private T content;
    private Integer numberOfElements;
}
