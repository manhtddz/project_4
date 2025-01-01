package com.example.e_project_4_api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {
    @Value("${file.upload.epj4-folder}")
    private String FOLDER;

    //    @Value("${file.upload.final-folder}")
//    private String FINAL_FOLDER;
    public String uploadImageFile(MultipartFile file) {
        try {
            // Đường dẫn folder lưu trữ
            String uploadFolder = FOLDER + "images/";

            // Tạo folder tạm nếu chưa tồn tại
            File tempDir = new File(uploadFolder);
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }

            // Lấy tên file gốc và kiểm tra hợp lệ
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || !originalFilename.contains(".")) {
                throw new IllegalArgumentException("Invalid file name.");
            }

            // Lấy phần mở rộng
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            // Tạo tên file mới với chuỗi ngẫu nhiên
            String randomString = UUID.randomUUID().toString();
            String newFilename = randomString + extension;

            // Lưu file vào folder tạm
            Path tempFilePath = Paths.get(uploadFolder, newFilename);
            Files.write(tempFilePath, file.getBytes());

            // Trả về tên file mới
            return newFilename;
        } catch (IOException e) {
            // Ghi log lỗi và ném ngoại lệ phù hợp
            return "";
//            throw new RuntimeException("Error while uploading file: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            // Ghi log lỗi liên quan đến dữ liệu đầu vào
            return "";
//            throw new RuntimeException("Invalid file input: " + e.getMessage(), e);
        }

    }

    public String uploadAudioFile(@RequestParam("file") MultipartFile file) {
        try {
            // Đường dẫn folder lưu trữ
            String uploadFolder = FOLDER + "audio/";

            // Tạo folder tạm nếu chưa tồn tại
            File tempDir = new File(uploadFolder);
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }

            // Lấy tên file gốc và kiểm tra hợp lệ
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || !originalFilename.contains(".")) {
                throw new IllegalArgumentException("Invalid file name.");
            }

            // Lấy phần mở rộng
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            // Tạo tên file mới với chuỗi ngẫu nhiên
            String randomString = UUID.randomUUID().toString();
            String newFilename = randomString + extension;

            // Lưu file vào folder tạm
            Path tempFilePath = Paths.get(uploadFolder, newFilename);
            Files.write(tempFilePath, file.getBytes());

            // Trả về tên file mới
            return newFilename;
        } catch (IOException e) {
            // Ghi log lỗi và ném ngoại lệ phù hợp
            return "";
//            throw new RuntimeException("Error while uploading file: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            // Ghi log lỗi liên quan đến dữ liệu đầu vào
            return "";
//            throw new RuntimeException("Invalid file input: " + e.getMessage(), e);
        }
    }

    public String uploadLRCFile(@RequestParam("file") MultipartFile file) {
        try {
            // Đường dẫn folder lưu trữ
            String uploadFolder = FOLDER + "lrc/";

            // Tạo folder tạm nếu chưa tồn tại
            File tempDir = new File(uploadFolder);
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }

            // Lấy tên file gốc và kiểm tra hợp lệ
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || !originalFilename.contains(".")) {
                throw new IllegalArgumentException("Invalid file name.");
            }

            // Lấy phần mở rộng
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            // Tạo tên file mới với chuỗi ngẫu nhiên
            String randomString = UUID.randomUUID().toString();
            String newFilename = randomString + extension;

            // Lưu file vào folder tạm
            Path tempFilePath = Paths.get(uploadFolder, newFilename);
            Files.write(tempFilePath, file.getBytes());

            // Trả về tên file mới
            return newFilename;
        } catch (IOException e) {
            // Ghi log lỗi và ném ngoại lệ phù hợp
            return "";
//            throw new RuntimeException("Error while uploading file: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            // Ghi log lỗi liên quan đến dữ liệu đầu vào
            return "";
//            throw new RuntimeException("Invalid file input: " + e.getMessage(), e);
        }
    }

    public void deleteFile(String fileName) {
        try {
            Files.deleteIfExists(Paths.get(FOLDER + "images/" + fileName));
        } catch (IOException ex) {
            // Log lỗi nhưng không throw để tránh che dấu lỗi chính
        }
    }
}
