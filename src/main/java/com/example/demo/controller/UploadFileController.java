package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.response.FileUploadResponse;
import com.example.demo.exception.ErrorCode;
import com.example.demo.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadFileController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public ApiResponse<?> uploadFile(@RequestParam("file") MultipartFile file){
        FileUploadResponse fileUploadResponse = uploadService.uploadFile(file);
        if(ErrorCode.SUCCESS.getCode() == fileUploadResponse.getMessageId()){
            return ApiResponse.builder().message("Upload successfully").build();
        }
        return ApiResponse.builder().code(fileUploadResponse.getMessageId()).message("Upload fail").build();
    }
}
