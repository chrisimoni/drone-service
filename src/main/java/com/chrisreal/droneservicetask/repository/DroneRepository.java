package com.chrisreal.droneservicetask.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chrisreal.droneservicetask.enums.DroneModel;
import com.chrisreal.droneservicetask.enums.DroneState;
import com.chrisreal.droneservicetask.model.Drone;

@Repository
public interface DroneRepository extends JpaRepository<Drone, String> {
    List<Drone> findByModel( DroneModel model);
    List<Drone> findByState( DroneState state);

}
