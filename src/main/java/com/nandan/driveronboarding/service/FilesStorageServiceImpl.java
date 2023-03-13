package com.nandan.driveronboarding.service;

import com.nandan.driveronboarding.entities.FileInfo;
import com.nandan.driveronboarding.repository.FileStorageRepository;
import com.nandan.driveronboarding.service.interfaces.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * This class implements the FilesStorageService interface which defines
 * methods for managing file storage. It provides methods for initializing, saving,
 * loading and deleting files, as well as persisting file metadata to a database.
 **/
@Service
public class FilesStorageServiceImpl implements FilesStorageService {

    @Autowired
    FileStorageRepository fileStorageRepository;

    private final Path root = Paths.get("uploads");

    /**
     * Initializes the file storage directory.
     *
     * @throws RuntimeException if the folder for upload could not be created.
     */
    @Override
    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    /**
     * Saves the given file to the file storage directory.
     *
     * @param file the file to be saved
     * @throws RuntimeException if the file could not be stored
     */
    @Override
    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    /**
     * Loads the file with the given filename from the file storage directory.
     *
     * @param filename the name of the file to be loaded
     * @return a Resource representing the loaded file
     * @throws RuntimeException if the file could not be read
     */
    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    /**
     * Deletes all files in the file storage directory.
     */
    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    /**
     * Loads all files in the file storage directory as a stream of Paths.
     *
     * @return a stream of Paths representing the loaded files
     * @throws RuntimeException if the files could not be loaded
     */
    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    /**
     * Persists the metadata of the given file to a database.
     *
     * @param file the file metadata to be persisted
     */
    @Override
    public void persistFile(FileInfo file) {
        fileStorageRepository.save(file);
    }
}
