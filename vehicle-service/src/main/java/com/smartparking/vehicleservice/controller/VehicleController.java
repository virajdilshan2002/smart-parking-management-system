package com.smartparking.vehicleservice.controller;

import com.smartparking.vehicleservice.dto.ResponseDTO;
import com.smartparking.vehicleservice.dto.VehicleDTO;
import com.smartparking.vehicleservice.service.VehicleService;
import com.smartparking.vehicleservice.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ResponseDTO> registerVehicle(@RequestHeader("Authorization") String authorization, @RequestBody VehicleDTO vehicleDTO) {
        try {
            int status = vehicleService.saveVehicle(vehicleDTO);
            switch (status) {
                case VarList.Created -> {
                    return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(VarList.Created, "Vehicle Registered Success", vehicleDTO.getLicensePlate()));
                }
                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ResponseDTO(VarList.Not_Acceptable, "Vehicle Already Added", null));
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }
}
