package com.smartparking.vehicleservice.service.impl;

import com.smartparking.vehicleservice.dto.ResponseDTO;
import com.smartparking.vehicleservice.dto.VehicleDTO;
import com.smartparking.vehicleservice.entity.Vehicle;
import com.smartparking.vehicleservice.feign.UserInterface;
import com.smartparking.vehicleservice.repo.VehicleRepository;
import com.smartparking.vehicleservice.service.VehicleService;
import com.smartparking.vehicleservice.util.VarList;
import com.smartparking.vehicleservice.util.VehicleType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserInterface userInterface;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public int saveVehicle(VehicleDTO vehicleDTO) {
        try {
            VehicleType type = VehicleType.valueOf(vehicleDTO.getType().toUpperCase());
            vehicleDTO.setType(type.name());
            if (!vehicleRepository.existsByLicensePlate(vehicleDTO.getLicensePlate())) {
                Boolean isExists = userInterface.existsByEmail(vehicleDTO.getUserEmail()).getBody();

                if (Boolean.TRUE.equals(isExists)) {
                    vehicleRepository.save(modelMapper.map(vehicleDTO, Vehicle.class));
                    return VarList.Created;
                }

                return VarList.Bad_Gateway;
            }
            return VarList.Not_Acceptable;
        } catch (IllegalArgumentException e) {
            return VarList.Not_Acceptable;
        }
    }
}
