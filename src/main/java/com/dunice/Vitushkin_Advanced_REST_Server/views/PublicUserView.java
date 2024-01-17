package com.dunice.Vitushkin_Advanced_REST_Server.views;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PublicUserView {
    private String avatar;
    private String email;
    private String id;
    private String name;
    private String role;
}
