package com.smartparking.parkingspaceservice.repo;

import com.smartparking.parkingspaceservice.entity.ParkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSpaceRepo extends JpaRepository<ParkingSpace,String> {
}
