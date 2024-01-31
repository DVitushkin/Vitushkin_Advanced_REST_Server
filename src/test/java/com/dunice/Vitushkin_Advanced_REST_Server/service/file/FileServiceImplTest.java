package com.dunice.Vitushkin_Advanced_REST_Server.service.file;

import com.dunice.Vitushkin_Advanced_REST_Server.storage.fileStorage.FileStorageImpl;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileServiceImplTest {
    @Mock
    private FileStorageImpl fileStorage;

    @InjectMocks
    private FileServiceImpl fileService;

    private static MockHttpServletRequest mockRequest;

    @BeforeAll
    public static void setup() {
        mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes attrs = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(attrs);
    }

    @Test
    @Epic(value = "Actions on file service")
    @Feature(value = "Upload file")
    @Description(value = "Uploading file on server")
    public void shouldReturnSuccessCreateNews() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "testFile.txt",
                MediaType.IMAGE_JPEG_VALUE,
                "Some Image".getBytes()
        );

        var result = fileService.uploadFile(file);
        assertEquals(true, result.getSuccess());
        assertEquals(1, result.getStatusCode());

        String[] downloadUrl = result.getData().split("/");
        String fileName = downloadUrl[downloadUrl.length -1];
        assertEquals(fileName, file.getOriginalFilename());
    }

    @Test
    @Epic(value = "Actions on file service")
    @Feature(value = "Get file")
    @Description(value = "Getting existing file")
    public void shouldReturnUrlResource() {
        String fileName = "testFile.jpg";
        UrlResource url = mock(UrlResource.class);
        when(url.getFilename())
                .thenReturn(fileName);

        when(fileStorage.load(fileName))
                .thenReturn(url);

         var result = fileService.loadFileByName(fileName);
         assertEquals(fileName, result.getFilename());
    }
}
