package com.files.filezipper.service;


import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class FileServiceTest {

    private final FileService fileService = new FileService();

    @Test
    public void shouldSaveFile() throws IOException {
        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        fileService.save(firstFile);
        Assert.assertEquals(1, new File(Paths.get("uploads").toUri()).listFiles().length);
        fileService.deleteAll();

    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenSaveFails() throws IOException {
        MockMultipartFile firstFile = new MockMultipartFile("data", "", "text/plain", "some xml".getBytes());
        fileService.save(firstFile);
    }

    @Test
    public void shouldGiveTheListOfFiles() throws IOException {
        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        MockMultipartFile secondFile = new MockMultipartFile("data", "other-file-name.data", "text/plain", "some other type".getBytes());

        fileService.save(firstFile);
        fileService.save(secondFile);
        File[] files = fileService.getFiles();

        Assert.assertEquals(files.length, new File(Paths.get("uploads").toUri()).listFiles().length);
        fileService.deleteAll();
    }


    @Test
    public void shouldDeleteAllFiles() throws IOException {

        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        MockMultipartFile secondFile = new MockMultipartFile("data", "other-file-name.data", "text/plain", "some other type".getBytes());

        fileService.save(firstFile);
        fileService.save(secondFile);

        fileService.deleteAll();

        Assert.assertEquals(0, new File(Paths.get("uploads").toUri()).listFiles().length);
    }

}