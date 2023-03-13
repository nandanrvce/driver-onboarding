package com.nandan.driveronboarding.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.nandan.driveronboarding.exception.BindingErrorsException;
import com.nandan.driveronboarding.requests.FileUploadRequest;
import com.nandan.driveronboarding.requests.VehicleInformationRequest;
import com.nandan.driveronboarding.responses.ResponseMessage;
import com.nandan.driveronboarding.usecase.VehicleRegistration;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DriverControllerTest {

    private static final String SUCCESS_MESSAGE = "success";

    @Mock
    private VehicleRegistration vehicleRegistration;

    @InjectMocks
    private DriverController driverController;

    @Mock
    BindingResult bindingResult;

    private Validator validator;
    private VehicleInformationRequest vehicleInformationRequest;
    private FileUploadRequest fileUploadRequest;

    @BeforeEach
    void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        fileUploadRequest = new FileUploadRequest();
        vehicleInformationRequest = new VehicleInformationRequest();
    }

    @Test
    @DisplayName("Upload Files should return OK response")
    void testUploadFiles() throws IOException {
        // Arrange
        FileUploadRequest fileUploadRequest = new FileUploadRequest();

        when(vehicleRegistration.storeAllFiles(fileUploadRequest)).thenReturn(SUCCESS_MESSAGE);

        // Act
        ResponseEntity<ResponseMessage> responseEntity = driverController.uploadFiles(fileUploadRequest, bindingResult);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(vehicleRegistration, times(1)).storeAllFiles(fileUploadRequest);
    }

    @SneakyThrows
    @Test
    @DisplayName("Vehicle Information should throw BindingErrorsException when request has validation errors")
    void testVehicleInformationWithValidationErrors() {
        // given
        FileUploadRequest fileUploadRequest = new FileUploadRequest();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        // when
        try {
            driverController.uploadFiles(fileUploadRequest, bindingResult);
        } catch (BindingErrorsException e) {
            // then
            verify(vehicleRegistration, never()).storeAllFiles(any(FileUploadRequest.class));
            verify(bindingResult, times(1)).hasErrors();
        }
    }
    @Test
    void uploadFiles_shouldReturnOkResponse_whenNoErrors(){
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(false);
        when(vehicleRegistration.storeAllFiles(fileUploadRequest)).thenReturn(String.valueOf(new HashMap<>()));

        // Act
        ResponseEntity<ResponseMessage> response = driverController.uploadFiles(fileUploadRequest, bindingResult);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(vehicleRegistration, times(1)).storeAllFiles(fileUploadRequest);
    }

    @Test
    void uploadFiles_shouldThrowBindingErrorsException_whenErrorsExist() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act and Assert
        assertThrows(BindingErrorsException.class, () -> driverController.uploadFiles(fileUploadRequest, bindingResult));
    }

    @Test
    void vehicleInformation_shouldReturnOkResponse_whenNoErrors() throws IOException {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(false);
        when(vehicleRegistration.persistVehicleData(vehicleInformationRequest)).thenReturn(ResponseEntity.ok().body(new ResponseMessage("Vehicle information persisted successfully")));

        // Act
        ResponseEntity<ResponseMessage> response = driverController.vehicleInformation(vehicleInformationRequest, bindingResult);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(vehicleRegistration, times(1)).persistVehicleData(vehicleInformationRequest);
    }

    @Test
    void vehicleInformation_shouldThrowBindingErrorsException_whenErrorsExist() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act and Assert
        assertThrows(BindingErrorsException.class, () -> driverController.vehicleInformation(vehicleInformationRequest, bindingResult));
    }
}
