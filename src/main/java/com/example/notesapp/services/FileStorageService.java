package com.example.notesapp.services;

import com.example.notesapp.utils.ApiResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileStorageService {
    public void init();

    public ApiResponse save(MultipartFile file);


    public Resource load(String filename);

    public void deleteAll();

    public Stream<Path> loadAll();
}
