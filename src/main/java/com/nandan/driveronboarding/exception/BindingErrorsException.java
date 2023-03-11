package com.nandan.driveronboarding.exception;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class BindingErrorsException extends RuntimeException {
    private final BindingResult bindingResult;

    public BindingErrorsException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

}
