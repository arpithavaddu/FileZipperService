package com.files.filezipper.controller;


import com.files.filezipper.service.FileService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FileZipperControllerTest {

    private MockMvc mockMvc;
    @Mock
    private FileService mockFileService;
    @Spy
    @InjectMocks
    private FilezipperController controller = new FilezipperController(mockFileService);

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void shouldGiveBadRequestWhenNoFileIsSentToZip() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("data", "filename.txt", "text/plain", "".getBytes());


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/files/zip").file("file", mockMultipartFile.getBytes()).contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().is(400)).andReturn();
        Assert.assertEquals(400, result.getResponse().getStatus());
        assertNotNull(result.getResponse().getContentAsString());
        Assert.assertTrue(result.getResponse().getContentAsString().contains("Please select at least one file to ZIP"));
    }

    @Test
    public void shouldGiveBadRequestWhenFileParameterIsNull() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/files/zip").file("file", null).contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().is(400)).andReturn();
        Assert.assertEquals(400, result.getResponse().getStatus());
        assertNotNull(result.getResponse().getContentAsString());
        Assert.assertTrue(result.getResponse().getContentAsString().contains("Please select at least one file to ZIP"));
    }

    @Test
    public void shouldGiveOKWhenFileIsSentToZip() throws Exception {
        Resource fileResource = new ClassPathResource("application.properties");

        MockMultipartFile firstFile = new MockMultipartFile(
                "file",fileResource.getFilename(),
                MediaType.MULTIPART_FORM_DATA_VALUE,
                fileResource.getInputStream());
        assertNotNull(firstFile);

        List<File> fileList = new ArrayList<>();
        fileList.add(fileResource.getFile());

        when(mockFileService.getFiles()).thenReturn((File[]) fileList.toArray(new File[fileList.size()]));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/files/zip").file(firstFile).contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        Assert.assertEquals(200, result.getResponse().getStatus());
        assertNotNull(result.getResponse().getContentAsString());
    }
}
