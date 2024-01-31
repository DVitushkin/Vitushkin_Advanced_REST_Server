package com.dunice.Vitushkin_Advanced_REST_Server.service.file;

import com.dunice.Vitushkin_Advanced_REST_Server.response.CustomSuccessResponse;

import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    CustomSuccessResponse<String> uploadFile(MultipartFile file);

    UrlResource loadFileByName(String fileName);
}
