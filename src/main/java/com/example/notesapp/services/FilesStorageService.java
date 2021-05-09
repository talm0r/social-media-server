package com.example.notesapp.services;


import com.example.notesapp.dtos.fileDtos.FileDto;
import com.example.notesapp.utils.ApiResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

@Service
public class FilesStorageService {
    private final Path root = Paths.get("uploads");
    private static String imageDirectory = System.getProperty("user.dir") + "/images";
    private static String URL = "http://localhost:8080/";


    /**
     * Checks if directory exists, if not - make it.
     * Then Save file inside directory and return path to user
     * @param file
     * @return fileDto
     */
    public ApiResponse save(MultipartFile file) {
        String message = "";
        makeDirectoryIfNotExist(imageDirectory);
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

    /**
     * load image from directory
     * @param filename
     * @return resource
     */
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



    // Make directory if not exist
    private void makeDirectoryIfNotExist(String imageDirectory) {
        File directory = new File(imageDirectory);
        if(!directory.exists()) {
            directory.mkdir();
        }
    }

    // Generate random name for image
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
