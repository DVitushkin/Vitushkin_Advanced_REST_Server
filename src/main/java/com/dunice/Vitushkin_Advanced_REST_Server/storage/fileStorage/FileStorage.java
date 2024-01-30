package com.dunice.Vitushkin_Advanced_REST_Server.storage.fileStorage;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorage{
    public void init();
    public void save(MultipartFile file);
    public UrlResource load(String filename);
    public void deleteByName(String filename);
}
