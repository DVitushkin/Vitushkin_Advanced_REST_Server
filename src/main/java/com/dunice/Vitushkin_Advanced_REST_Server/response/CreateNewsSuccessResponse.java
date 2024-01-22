package com.dunice.Vitushkin_Advanced_REST_Server.response;

import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true)
public class CreateNewsSuccessResponse extends BaseSuccessResponse{
    private Long id;

    CreateNewsSuccessResponse(Integer statusCode, Boolean success) {
        super(statusCode, success);
    }

    public static CreateNewsSuccessResponse ok(Long id) {
        return new CreateNewsSuccessResponse(1, true).setId(id);
    }

}
