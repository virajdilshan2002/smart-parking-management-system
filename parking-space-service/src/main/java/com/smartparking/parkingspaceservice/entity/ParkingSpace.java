package com.smartparking.parkingspaceservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "parking_spaces")
public class ParkingSpace {
    @Id
    private String spaceCode;
    private boolean available;
    private String location;
    private String ownerEmail;
}

