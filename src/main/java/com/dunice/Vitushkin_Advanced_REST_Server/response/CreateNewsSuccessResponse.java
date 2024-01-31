package com.dunice.Vitushkin_Advanced_REST_Server.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CreateNewsSuccessResponse extends BaseSuccessResponse {
    private Long id;

    public static CreateNewsSuccessResponse ok(Long id) {
        return CreateNewsSuccessResponse
                .builder()
                .id(id)
                .statusCode(1)
                .success(true)
                .build();
    }
}
