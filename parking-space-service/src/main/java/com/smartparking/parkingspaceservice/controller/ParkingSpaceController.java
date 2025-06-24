package com.smartparking.parkingspaceservice.controller;

import com.smartparking.parkingspaceservice.dto.ParkingSpaceDTO;
import com.smartparking.parkingspaceservice.dto.ResponseDTO;
import com.smartparking.parkingspaceservice.service.ParkingSpaceService;
import com.smartparking.parkingspaceservice.util.VarList;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parking-spaces")
public class ParkingSpaceController {

    private final ParkingSpaceService parkingSpaceService;

    public ParkingSpaceController(ParkingSpaceService parkingSpaceService) {
        this.parkingSpaceService = parkingSpaceService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    ResponseEntity<ResponseDTO> createParkingSpace(@RequestHeader("Authorization") String authorization, @RequestBody ParkingSpaceDTO parkingSpace) {
        int status = parkingSpaceService.save(parkingSpace);
        switch (status) {
            case VarList.Created -> {
                return ResponseEntity.status(VarList.Created).body(
                        new ResponseDTO(VarList.Created, "Parking space created successfully", parkingSpace)
                );
            }
            case VarList.Not_Acceptable -> {
                return ResponseEntity.status(VarList.Not_Acceptable).body(
                        new ResponseDTO(VarList.Not_Acceptable, "Parking space already exists", null)
                );
            }
            default -> {
                return ResponseEntity.status(VarList.Internal_Server_Error).body(
                        new ResponseDTO(VarList.Internal_Server_Error, "Failed to create parking space", null)
                );
            }
        }
    }
}
