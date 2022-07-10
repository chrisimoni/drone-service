package com.chrisreal.droneservicetask.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chrisreal.droneservicetask.model.Medication;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, String> {
  List<Medication> findByStatus(boolean status);
  List<Medication> findByName(String name);
}
