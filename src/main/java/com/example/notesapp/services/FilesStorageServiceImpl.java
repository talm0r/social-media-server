package com.example.notesapp.services;


import com.example.notesapp.dtos.fileDtos.FileDto;
import com.example.notesapp.utils.ApiResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Stream;

@Service
public class FilesStorageServiceImpl implements FileStorageService {
    private final Path root = Paths.get("uploads");
    private static String imageDirectory = System.getProperty("user.dir") + "/images";
    private static String URL = "http://localhost:8080/";
    @Override
    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public ApiResponse save(MultipartFile file) {
        String message = "";
        makeDirectoryIfNotExist(imageDirectory);
        Path fileNamePath = Paths.get(imageDirectory,file.getOriginalFilename().concat(".").concat(FilenameUtils.getExtension(file.getOriginalFilename())));

        try {
            Path savedPath = Paths.get(getRandomPath().concat(".").concat(FilenameUtils.getExtension(file.getOriginalFilename())));
            Files.copy(file.getInputStream(), this.root.resolve(savedPath));
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            FileDto fileDto = new FileDto();
            fileDto.setName(file.getOriginalFilename());
            fileDto.setUrl(URL + "upload/files/"+savedPath );
            return new ApiResponse(200,message,fileDto);
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
    private void makeDirectoryIfNotExist(String imageDirectory) {
        File directory = new File(imageDirectory);
        if(!directory.exists()) {
            directory.mkdir();
        }
    }
    private String getRandomPath() {
        int randomNumber = (int) (Math.random() * 100000);
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return  generatedString + String.valueOf(randomNumber);

    }
}
