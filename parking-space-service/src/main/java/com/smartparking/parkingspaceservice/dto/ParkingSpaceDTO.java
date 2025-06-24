package com.smartparking.parkingspaceservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ParkingSpaceDTO {
    private String spaceCode;
    private boolean available;
    private String location;
    private String ownerEmail;
}

