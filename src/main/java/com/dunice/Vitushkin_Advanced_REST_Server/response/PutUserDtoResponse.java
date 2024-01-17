package com.dunice.Vitushkin_Advanced_REST_Server.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PutUserDtoResponse {
    String avatar;
    String email;
    String id;
    String name;
    String role;
}
