package com.chrisreal.droneservicetask.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chrisreal.droneservicetask.model.Dispatch;
import com.chrisreal.droneservicetask.model.Drone;
import com.chrisreal.droneservicetask.model.Medication;

@Repository
public interface DispatchRepository extends JpaRepository<Dispatch, String> {
    List<Dispatch> findByDrone( Drone drone);
    List<Dispatch> findByMedication( Medication medication);
}
