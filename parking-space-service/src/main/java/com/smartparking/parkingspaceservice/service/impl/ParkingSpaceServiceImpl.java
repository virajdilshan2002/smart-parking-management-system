package com.smartparking.parkingspaceservice.service.impl;

import com.smartparking.parkingspaceservice.dto.ParkingSpaceDTO;
import com.smartparking.parkingspaceservice.entity.ParkingSpace;
import com.smartparking.parkingspaceservice.repo.ParkingSpaceRepo;
import com.smartparking.parkingspaceservice.service.ParkingSpaceService;
import com.smartparking.parkingspaceservice.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ParkingSpaceServiceImpl implements ParkingSpaceService {

    private final ParkingSpaceRepo parkingSpaceRepo;
    private final ModelMapper modelMapper;

    public ParkingSpaceServiceImpl(ParkingSpaceRepo parkingSpaceRepo, ModelMapper modelMapper) {
        this.parkingSpaceRepo = parkingSpaceRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public int save(ParkingSpaceDTO parkingSpace) {
        if (!parkingSpaceRepo.existsById(parkingSpace.getSpaceCode())){
            parkingSpaceRepo.save(modelMapper.map(parkingSpace, ParkingSpace.class));
            return VarList.Created;
        }

        return VarList.Not_Acceptable;
    }
}
