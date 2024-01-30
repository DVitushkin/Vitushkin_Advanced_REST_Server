package com.dunice.Vitushkin_Advanced_REST_Server.service.file;

import com.dunice.Vitushkin_Advanced_REST_Server.response.CustomSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.storage.fileStorage.FileStorageImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileStorageImpl fileStorage;

    @Override
    public CustomSuccessResponse<String> uploadFile(MultipartFile file){
        fileStorage.save(file);
        String downloadUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/v1")
                .path("/file/")
                .path(Objects.requireNonNull(file.getOriginalFilename()))
                .toUriString();
        return CustomSuccessResponse.withData(downloadUrl);
    }
    @Override
    public UrlResource loadFileByName(String fileName) {
        return fileStorage.load(fileName);

    }
}
