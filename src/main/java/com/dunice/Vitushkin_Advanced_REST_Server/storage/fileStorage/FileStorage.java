package com.dunice.Vitushkin_Advanced_REST_Server.storage.fileStorage;

import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {
    void init();
    void save(MultipartFile file);
    UrlResource load(String filename);
}
