package com.nandan.driveronboarding.requests;

import com.nandan.driveronboarding.enums.DriverRideAvailabilityStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverRideStatusRequest {
    DriverRideAvailabilityStatus status;
}
