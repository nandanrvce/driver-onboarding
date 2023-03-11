package com.nandan.driveronboarding.repository;

import com.nandan.driveronboarding.entities.FileInfo;
import com.nandan.driveronboarding.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileStorageRepository extends JpaRepository<FileInfo, Long> {
    Optional<List<FileInfo>> findByUser(User user);
}
