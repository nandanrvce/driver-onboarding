package com.nandan.driveronboarding.repository;

import com.nandan.driveronboarding.entities.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileStorageRepository extends JpaRepository<FileInfo, Long> {
}
