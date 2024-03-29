package com.dunice.Vitushkin_Advanced_REST_Server.views;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PublicUserView {
    private String avatar;
    private String email;
    private String id;
    private String name;
    private String role;
}
