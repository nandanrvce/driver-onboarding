package com.nandan.driveronboarding.requests;

import com.nandan.driveronboarding.validator.ValidFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadRequest {
    @ValidFile(message = "rc not valid")
    MultipartFile rc;
    @ValidFile(message = "dl not valid")
    MultipartFile dl;
    @ValidFile(message = "pic not valid")
    MultipartFile pic;
}
