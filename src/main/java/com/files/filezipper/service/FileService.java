package com.files.filezipper.service;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
    private final Path root = Paths.get("uploads");

    public void save(MultipartFile file) {
        try {
            if(!Files.exists(root)){
                Files.createDirectory(root);
            }
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public File[] getFiles() {
        File file = new File(this.root.toUri());
        return file.listFiles();
    }

    public void deleteAll() throws IOException {
        FileUtils.cleanDirectory(this.root.toFile());
    }
}
