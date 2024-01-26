package com.dunice.Vitushkin_Advanced_REST_Server.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class BaseSuccessResponse {
    private Integer statusCode;
    private Boolean success;

    public static BaseSuccessResponse ok() {
        return BaseSuccessResponse.builder()
                .statusCode(1)
                .success(true)
                .build();
    }
}
