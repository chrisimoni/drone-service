package com.chrisreal.droneservicetask.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chrisreal.droneservicetask.dto.DroneDto;
import com.chrisreal.droneservicetask.enums.DroneModel;
import com.chrisreal.droneservicetask.enums.DroneState;
import com.chrisreal.droneservicetask.exception.BadRequestException;
import com.chrisreal.droneservicetask.exception.DuplicateException;
import com.chrisreal.droneservicetask.exception.NotFoundException;
import com.chrisreal.droneservicetask.model.Drone;
import com.chrisreal.droneservicetask.repository.DroneRepository;
import com.chrisreal.droneservicetask.response.DroneResponse;
import com.chrisreal.droneservicetask.validators.Validator;

@Service
public class DroneService {
	@Autowired
	Validator validator;

	@Autowired
	DroneRepository droneRepository;

	public DroneResponse registerDrone(DroneDto droneDto) throws BadRequestException, DuplicateException {
		validator.validateDrones(droneDto);
		Optional<Drone> drone = droneRepository.findById(droneDto.getSerialNo());
		if (drone.isEmpty()) {
			droneRepository.save(droneDto.toModel());
		} else {
			throw new DuplicateException("Record already exists with the specified serial number.");
		}
		return new DroneResponse("201", "Record saved successfully.");
	}

	public DroneResponse getDroneByModel(String model) throws NotFoundException, BadRequestException {
		List<Drone> drones = droneRepository.findByModel(DroneModel.get(model));
		if (drones.size() == 0)
			throw new NotFoundException("No record(s) found for the given model");

		return new DroneResponse("200", "Drones retrieved successfully.", drones);
	}

	public DroneResponse getDronesByState(String state) throws BadRequestException, NotFoundException {
		List<Drone> drones = droneRepository.findByState(DroneState.get(state));
		if (drones.size() == 0)
			throw new NotFoundException("No record(s) found for the given state");

		return new DroneResponse("200", "Drones retrieved successfully.", drones);
	}

	public DroneResponse getIdleDrones() throws NotFoundException {
		List<Drone> drones = droneRepository.findByState(DroneState.IDLE);
		if (drones.size() == 0)
			throw new NotFoundException("No record(s) found for the given state");

		return new DroneResponse("200", "Drones retrieved successfully.", drones);
	}

	public DroneResponse getDronesBySerialNo(String serialNo) throws NotFoundException {
		Optional<Drone> drones = droneRepository.findById(serialNo);
		if (drones.isEmpty())
			throw new NotFoundException("No drone found with the given serial number");

		return new DroneResponse("200", "Drones retrieved successfully.", drones.get());
	}
}
