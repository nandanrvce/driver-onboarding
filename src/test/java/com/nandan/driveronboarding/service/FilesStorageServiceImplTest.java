package com.nandan.driveronboarding.service;

import com.nandan.driveronboarding.entities.FileInfo;
import com.nandan.driveronboarding.enums.FileStatus;
import com.nandan.driveronboarding.repository.FileStorageRepository;
import com.nandan.driveronboarding.service.interfaces.FilesStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class FilesStorageServiceImplTest {

    private FilesStorageService filesStorageService;

    @Mock
    private FileStorageRepository fileStorageRepository;

    @BeforeEach
    public void setUp() {
        filesStorageService = new FilesStorageServiceImpl();
        filesStorageService.init();
    }
}
