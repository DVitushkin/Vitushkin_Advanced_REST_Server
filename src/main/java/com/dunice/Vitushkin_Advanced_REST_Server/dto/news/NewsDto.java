package com.dunice.Vitushkin_Advanced_REST_Server.dto.news;

import com.dunice.Vitushkin_Advanced_REST_Server.exception.ErrorsMsg;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NewsDto {
    @NotBlank(message = ErrorsMsg.NEWS_DESCRIPTION_NOT_NULL)
    @Size(min = 3, max = 160, message = ErrorsMsg.NEWS_DESCRIPTION_SIZE_NOT_VALID)
    private String description;

    @NotBlank(message = ErrorsMsg.NEWS_IMAGE_HAS_TO_BE_PRESENT)
    @Size(min = 3, max = 130, message = ErrorsMsg.NEWS_IMAGE_LENGTH)
    private String image;

    @NotEmpty(message = ErrorsMsg.TAGS_NOT_VALID)
    private List<@NotBlank(message = ErrorsMsg.TAGS_NOT_VALID) String> tags;

    @NotBlank(message = ErrorsMsg.NEWS_TITLE_NOT_NULL)
    @Size(min = 3, max = 160, message = ErrorsMsg.NEWS_TITLE_SIZE)
    private String title;
}