package com.nandan.driveronboarding.entities;

import com.nandan.driveronboarding.enums.FileStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FileInfo {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String url;
    private FileStatus status;
    private String statusComments;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;}