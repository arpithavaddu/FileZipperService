package com.files.filezipper.controller;

import com.files.filezipper.exception.NoFilesSelectedException;
import com.files.filezipper.service.FileService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/files")
public class FilezipperController {

    private FileService fileService;

    @Autowired
    public FilezipperController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/zip", produces = "application/zip")
    public void zipFiles(@RequestParam("file") MultipartFile[] files, HttpServletResponse response) throws IOException, NoFilesSelectedException {

        if (files == null || files.length == 0 || files[0].isEmpty()) {
            throw new NoFilesSelectedException("Please select at least one file to ZIP");
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\"test.zip\"");
        ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());
        Arrays.stream(files).forEach(file -> fileService.save(file));

        for (File file : fileService.getFiles()) {
            zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
            FileInputStream fileInputStream = new FileInputStream(file);

            IOUtils.copy(fileInputStream, zipOutputStream);

            fileInputStream.close();
            zipOutputStream.closeEntry();
        }
        zipOutputStream.close();
        fileService.deleteAll();
    }
}
