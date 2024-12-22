package com.example.e_project_4_api.controllers;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
public class FileController {


    @Value("${file.upload.epj4-folder}")
    private String FOLDER;

//    @Value("${file.upload.final-folder}")
//    private String FINAL_FOLDER;

    @PostMapping("/upload/image")
    public ResponseEntity<String> uploadImageFile(@RequestParam("file") MultipartFile file) {
        try {
            // Tạo folder tạm nếu chưa tồn tại
            File tempDir = new File(FOLDER + "images/");
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }

            // Lấy tên file gốc
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            // Tạo tên file mới với chuỗi ngẫu nhiên
            String randomString = UUID.randomUUID().toString();
            String newFilename = randomString + extension;

            // Lưu file vào folder tạm
            Path tempFilePath = Paths.get(FOLDER + "images/" + newFilename);
            Files.write(tempFilePath, file.getBytes());

            // Trả về tên file mới
            return ResponseEntity.ok(newFilename);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
        }
    }

    @PostMapping("/upload/audio")
    public ResponseEntity<String> uploadAudioFile(@RequestParam("file") MultipartFile file) {
        try {
            // Tạo folder tạm nếu chưa tồn tại
            File tempDir = new File(FOLDER + "audio/");
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }

            // Lấy tên file gốc
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            // Tạo tên file mới với chuỗi ngẫu nhiên
            String randomString = UUID.randomUUID().toString();
            String newFilename = randomString + extension;

            // Lưu file vào folder tạm
            Path tempFilePath = Paths.get(FOLDER + "audio/" + newFilename);
            Files.write(tempFilePath, file.getBytes());

            // Trả về tên file mới
            return ResponseEntity.ok(newFilename);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
        }
    }

    @PostMapping("/upload/lrc")
    public ResponseEntity<String> uploadLRCFile(@RequestParam("file") MultipartFile file) {
        try {
            // Tạo folder tạm nếu chưa tồn tại
            File tempDir = new File(FOLDER + "lrc/");
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }

            // Lấy tên file gốc
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            // Tạo tên file mới với chuỗi ngẫu nhiên
            String randomString = UUID.randomUUID().toString();
            String newFilename = randomString + extension;

            // Lưu file vào folder tạm
            Path tempFilePath = Paths.get(FOLDER + "lrc/" + newFilename);
            Files.write(tempFilePath, file.getBytes());

            // Trả về tên file mới
            return ResponseEntity.ok(newFilename);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
        }
    }

//    @PostMapping("/submit")
//    public ResponseEntity<String> submitFile(@RequestBody FileSubmissionRequest request) {
//        try {
//            String tempFileName = request.getTempFileName();
//
//            // Kiểm tra file tạm có tồn tại không
//            Path tempFilePath = Paths.get(TEMP_FOLDER + tempFileName);
//            if (!Files.exists(tempFilePath)) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Temporary file not found.");
//            }
//
//            // Tạo folder chính nếu chưa tồn tại
//            File finalDir = new File(FINAL_FOLDER);
//            if (!finalDir.exists()) {
//                finalDir.mkdirs();
//            }
//
//            // Di chuyển file từ folder tạm sang folder chính
//            Path finalFilePath = Paths.get(FINAL_FOLDER + tempFileName);
//            Files.move(tempFilePath, finalFilePath, StandardCopyOption.REPLACE_EXISTING);
//
//            // Lưu thông tin file vào database (giả sử bạn có repository)
//            // fileRepository.save(new FileEntity(tempFileName));
//
//            return ResponseEntity.ok("File saved successfully.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save file.");
//        }
//    }

//    public static class FileSubmissionRequest {
//        private String tempFileName;
//
//        public String getTempFileName() {
//            return tempFileName;
//        }
//
//        public void setTempFileName(String tempFileName) {
//            this.tempFileName = tempFileName;
//        }
//    }


    @GetMapping("/download/lrc/{filename}")
    public ResponseEntity<?> downloadLRCFile(@PathVariable("filename") String filename) throws IOException {
        String filePath = FOLDER + "lrc/" + filename;

        File file = new File(filePath);

        // Kiểm tra xem file có tồn tại không
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found.");
        }

        // Đọc nội dung file
        byte[] fileContent = Files.readAllBytes(file.toPath());

        // Trả về file với đúng Content-Type và tên file trong header
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)  // Content-Type cho file ảnh JPEG
                .body(fileContent);
    }

    @GetMapping("/download/audio/{filename}")
    public ResponseEntity<?> downloadAudioFile(@PathVariable("filename") String filename) throws IOException {
        String filePath = FOLDER + "audio/" + filename;

        File file = new File(filePath);

        // Kiểm tra xem file có tồn tại không
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found.");
        }

        // Đọc nội dung file
        byte[] fileContent = Files.readAllBytes(file.toPath());

        // Trả về file với đúng Content-Type và tên file trong header
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("audio/mpeg"))  // Content-Type cho file ảnh JPEG
                .body(fileContent);
    }

    @GetMapping("/download/image/{filename}")
    public ResponseEntity<?> downloadImageFile(@PathVariable("filename") String filename) throws IOException {
        String filePath = FOLDER + "images/" + filename;

        File file = new File(filePath);

        // Kiểm tra xem file có tồn tại không
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found.");
        }

        // Đọc nội dung file
        byte[] fileContent = Files.readAllBytes(file.toPath());

        // Trả về file với đúng Content-Type và tên file trong header
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)  // Content-Type cho file ảnh JPEG
                .body(fileContent);
    }
}

