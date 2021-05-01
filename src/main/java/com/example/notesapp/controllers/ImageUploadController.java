package com.example.notesapp.controllers;

import com.example.notesapp.services.FilesStorageServiceImpl;
import com.example.notesapp.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("upload")
@CrossOrigin
public class ImageUploadController {
    private static String imageDirectory = System.getProperty("user.dir") + "/images";
    private static String URL = "http://localhost:8080/";

    @Autowired
    FilesStorageServiceImpl storageService;
    @PostMapping("/upload")
    public ApiResponse uploadFile(@RequestParam("file") MultipartFile file) {

        String message = "";

        try {

          return  storageService.save(file);
//            int randomNumber = (int) (Math.random() * 100000);
//            message = "Uploaded the file successfully: " + file.getOriginalFilename();
//            FileDto fileDto = new FileDto();
//            fileDto.setName(file.getOriginalFilename());
//            fileDto.setUrl(URL + "files/"+file.getOriginalFilename());
//            return new ApiResponse(200,message,fileDto);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return new ApiResponse(400,message,null);
        }
    }



    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        System.out.println(filename);
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}


