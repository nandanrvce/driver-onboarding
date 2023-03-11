package com.nandan.driveronboarding.usecase;

import com.nandan.driveronboarding.entities.FileInfo;
import com.nandan.driveronboarding.entities.User;
import com.nandan.driveronboarding.entities.UserContextHolder;
import com.nandan.driveronboarding.enums.FileStatus;
import com.nandan.driveronboarding.repository.FileStorageRepository;
import com.nandan.driveronboarding.repository.UserRepository;
import com.nandan.driveronboarding.requests.FileUploadRequest;
import com.nandan.driveronboarding.service.interfaces.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class VehicleRegistration {
    @Autowired
    FilesStorageService storageService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FileStorageRepository fileStorageRepository;
    public String storeAllFiles(FileUploadRequest fileUploadRequest) throws IOException {
        Long userId = UserContextHolder.getContext().getUserId();
        storageService.save(fileUploadRequest.getDl());
        storageService.save(fileUploadRequest.getRc());
        storageService.save(fileUploadRequest.getPic());
        User user = userRepository.findById(userId).get();
        persistFile(user, fileUploadRequest.getDl().getOriginalFilename());
        persistFile(user, fileUploadRequest.getRc().getOriginalFilename());
        persistFile(user, fileUploadRequest.getPic().getOriginalFilename());
        List<String> fileNames = new ArrayList<>();
        fileNames.add(fileUploadRequest.getRc().getOriginalFilename());
        fileNames.add(fileUploadRequest.getDl().getOriginalFilename());
        fileNames.add(fileUploadRequest.getPic().getOriginalFilename());
        String message = "Uploaded the files successfully: " + fileNames;
        return message;
    }

    private void persistFile(User user, String fileName) throws IOException {
        String filePath = storageService.load(fileName).getFile().getCanonicalPath();
        FileInfo file = FileInfo.builder()
                .url(filePath)
                .name(fileName)
                .user(user)
                .status(FileStatus.SUBMITTED)
                .build();
        fileStorageRepository.save(file);
    }
}
