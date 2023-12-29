package com.dunice.Vitushkin_Advanced_REST_Server.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
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
