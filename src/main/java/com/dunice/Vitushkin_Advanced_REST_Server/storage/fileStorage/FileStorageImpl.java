package com.dunice.Vitushkin_Advanced_REST_Server.storage.fileStorage;

import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileStorageImpl implements FileStorage{
    private final Path rootLocation = Paths.get("upload-dir");

    FileStorageImpl(){
        init();
    }
    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }catch (IOException ex){
            throw new FileStorageException(ex.getMessage());
        }
    }

    @Override
    public void save(MultipartFile file) {
        Path destinationFile = this.rootLocation
                .toAbsolutePath()
                .resolve(Paths.get(file.getOriginalFilename()).normalize());
        try {
            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException ex){
            throw new FileStorageException(ex.getMessage());
        }
    }

    @Override
    public UrlResource load(String filename) {
        Path file = rootLocation.resolve(filename);
        try {
            UrlResource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileStorageException("Could not read the file!");
            }

        } catch (MalformedURLException ex) {
            throw new FileStorageException(ex.getMessage());
        }
    }

    @Override
    public void deleteByName(String filename) {

    }
}
