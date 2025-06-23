package com.smartparking.vehicleservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    private String licensePlate;
    private String category;
    private String model;
    private String userEmail;
}
