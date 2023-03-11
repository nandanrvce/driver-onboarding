package com.nandan.driveronboarding.service;

import com.nandan.driveronboarding.requests.FileUploadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
@Service
public class ValidationService {
    private Validator validator;

    ValidationService() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public void validateAllFile(FileUploadRequest input) {
        Set<ConstraintViolation<MultipartFile>> violationsRc = validator.validate(input.getRc());
        Set<ConstraintViolation<MultipartFile>> violationsDl = validator.validate(input.getDl());
        Set<ConstraintViolation<MultipartFile>> violationsPic = validator.validate(input.getPic());
        if (!violationsRc.isEmpty()) {
            throw new ConstraintViolationException(violationsRc);
        }
    }
}
