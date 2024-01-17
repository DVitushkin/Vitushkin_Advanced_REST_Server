package com.dunice.Vitushkin_Advanced_REST_Server.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomSuccessResponse<T> {
    private T data;
    private Integer statusCode;
    private Boolean success;
    private Integer[] codes;
    private String msg;

    public static CustomSuccessResponse<?> ok() {
        return CustomSuccessResponse.builder()
                .success(true)
                .statusCode(1)
                .build();
    }

    public static <T> CustomSuccessResponse<T> withData(T data) {
        return CustomSuccessResponse.<T>builder()
                .data(data)
                .success(true)
                .statusCode(1)
                .build();
    }

    public static CustomSuccessResponse<?> withCode(Integer... code) {
        return CustomSuccessResponse.builder()
                .success(true)
                .statusCode(code[0])
                .codes(code)
                .build();
    }
}
