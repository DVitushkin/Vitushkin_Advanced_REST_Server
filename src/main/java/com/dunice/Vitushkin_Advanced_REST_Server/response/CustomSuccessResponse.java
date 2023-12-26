package com.dunice.Vitushkin_Advanced_REST_Server.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@NoArgsConstructor
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

    public static <T> CustomSuccessResponse<T> data(T data) {
        return CustomSuccessResponse.<T>builder()
                .data(data)
                .success(true)
                .statusCode(1)
                .build();
    }

    public static ResponseEntity<CustomSuccessResponse<?>> badRequest(Integer code) {
        return ResponseEntity.badRequest()
                .body(CustomSuccessResponse.builder()
                        .success(true)
                        .statusCode(code)
                        .codes(new Integer[] { code })
                        .build()
        );
    }

    public static ResponseEntity<CustomSuccessResponse<?>> badRequest(Integer[] codes) {
        Integer indexOfFirstError = 0;
        return ResponseEntity.badRequest()
                .body(CustomSuccessResponse.builder()
                        .success(true)
                        .statusCode(codes[indexOfFirstError])
                        .codes(codes)
                        .build());
    }

    public static ResponseEntity<CustomSuccessResponse<?>> badRequest(String msg) {
        return ResponseEntity.badRequest()
                .body(CustomSuccessResponse.builder()
                        .success(true)
                        .msg(msg)
                        .build());
    }
}
