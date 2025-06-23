package com.smartparking.vehicleservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VehicleDTO {
    private String licensePlate;
    private String category;
    private String model;
    private String userEmail;
}
