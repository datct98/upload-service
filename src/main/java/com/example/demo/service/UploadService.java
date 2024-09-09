package com.example.demo.service;

import com.example.demo.dto.response.FileUploadResponse;
import com.example.demo.entities.FileUpload;
import com.example.demo.repository.FileUploadRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
@Slf4j
@Service
public class UploadService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private FileUploadRepository fileUploadRepository;

    public FileUploadResponse uploadFile(MultipartFile file){
        try {
            // Tạo thư mục nếu nó không tồn tại
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Lưu file
            String filePath = uploadDir + File.separator + file.getOriginalFilename();
            file.transferTo(new File(filePath));
            FileUpload fileUpload = new FileUpload(filePath);
            fileUploadRepository.save(fileUpload);
            return new FileUploadResponse().builder().messageId(1000).build();

        } catch (Exception e){
            log.error("uploadFile fail: {}", e);
            return new FileUploadResponse().builder().messageId(1001).build();
        }
    }
}
