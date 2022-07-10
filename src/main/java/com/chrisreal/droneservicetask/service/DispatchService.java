package com.chrisreal.droneservicetask.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chrisreal.droneservicetask.dto.DispatchDto;
import com.chrisreal.droneservicetask.enums.DroneState;
import com.chrisreal.droneservicetask.exception.BadRequestException;
import com.chrisreal.droneservicetask.exception.NotFoundException;
import com.chrisreal.droneservicetask.model.Dispatch;
import com.chrisreal.droneservicetask.model.Drone;
import com.chrisreal.droneservicetask.model.Medication;
import com.chrisreal.droneservicetask.repository.DispatchRepository;
import com.chrisreal.droneservicetask.response.DispatchResponse;
import com.chrisreal.droneservicetask.response.DroneResponse;

@Service
public class DispatchService {
	@Autowired
	DroneService droneService;
	@Autowired
	MedicationService medicationService;

	@Autowired
	DispatchRepository dispatchRepository;

	private String generateId() {
		return new Date().getTime() + generateFourDigitRandomID();
	}

	private String generateFourDigitRandomID() {
		SecureRandom rand = new SecureRandom();
		return (rand.nextInt(8999) + 1000) + "";
	}

	public DispatchResponse setUpDispatch(DispatchDto dispatchDto) throws BadRequestException {
		if (dispatchDto.getMedicationCode() == null || dispatchDto.getDroneSerialNo() == null)
			throw new BadRequestException("medicationCode and droneSerialNo should not be null");

		Dispatch dispatch;
		try {
			Drone drones = droneService.getDronesBySerialNo(dispatchDto.getDroneSerialNo()).getDrone();
			if (drones.getBatteryCapacity() < 25) {
				throw new BadRequestException("Drone battery is low");
			}

			Medication medication = medicationService.getMedicationsByCode(dispatchDto.getMedicationCode())
					.getMedication();
			if (!medication.getStatus()) {
				throw new BadRequestException("Medication is currently disabled");
			}

			List<Dispatch> dispatchList = dispatchRepository.findByDrone(drones);

			int droneUsedSpace = getDroneUsedSpace(dispatchList);
			int droneAvailableSpace = drones.getWeightLimit().intValue() - droneUsedSpace;
			if (droneAvailableSpace < (droneUsedSpace + medication.getWeight())) {
				throw new BadRequestException(
						"Medication weight is higher than the drone's available space, kindly add a medication with weight below "
								+ droneAvailableSpace);
			}

			LocalDateTime date = LocalDateTime.now();
			dispatchDto.setTimestamp(date);
			dispatchDto.setId(generateId());
			dispatchDto.setDrone(drones);
			dispatchDto.setMedication(medication);
			dispatch = dispatchDto.toModel();
			dispatchRepository.save(dispatch);

			if (droneAvailableSpace < 10) {
				droneService.updateDroneState(dispatchDto.getDroneSerialNo(), DroneState.LOADED);
			} else {
				droneService.updateDroneState(dispatchDto.getDroneSerialNo(), DroneState.LOADING);
			}
		} catch (NotFoundException e) {
			throw new BadRequestException(e.getMessage());
		}
		return new DispatchResponse("200", "Dispatch setup successfully. Tracker ID is " + dispatchDto.getId(),
				dispatch);
	}

	public DispatchResponse triggerDispatch(String droneSerialNumber) throws NotFoundException {
		List<Dispatch> dispatch = dispatchRepository
				.findByDrone(droneService.getDronesBySerialNo(droneSerialNumber).getDrone());
		if (dispatch.isEmpty())
			throw new NotFoundException("Drone is not yet loaded");

		droneService.updateDroneState(droneSerialNumber, DroneState.DELIVERING);
		return new DispatchResponse("200", "Dispatch triggered successfully.", dispatch);
	}

	public DispatchResponse getDroneContent(String droneSerialNumber) throws NotFoundException {
		List<Dispatch> dispatch = dispatchRepository
				.findByDrone(droneService.getDronesBySerialNo(droneSerialNumber).getDrone());
		if (dispatch.isEmpty())
			throw new NotFoundException("Drone is not yet loaded");

		return new DispatchResponse("200", "Records fetched", dispatch);
	}

	public DispatchResponse getDronesByMedication(String medicationCode) throws NotFoundException {
		List<Dispatch> dispatch = dispatchRepository
				.findByMedication(medicationService.getMedicationsByCode(medicationCode).getMedication());
		if (dispatch.isEmpty())
			throw new NotFoundException("Medication is not yet added to any drone");

		return new DispatchResponse("200", "Records fetched", dispatch);
	}

	public DroneResponse getAvailableDrones() {
		List<Drone> drones = droneService.retrieveAllDrones().getDrones();
		List<Drone> availableDrones = new ArrayList<>();
		drones.forEach(drone -> {
			List<Dispatch> dispatchList = dispatchRepository.findByDrone(drone);
			int droneUsedSpace = getDroneUsedSpace(dispatchList);
			int droneAvailableSpace = drone.getWeightLimit().intValue() - droneUsedSpace;
			if (droneAvailableSpace > 10 && "IDLE,LOADING".contains(drone.getState().name())) {
				availableDrones.add(drone);
			}
		});

		return new DroneResponse("200", "Records fetched", availableDrones);
	}

	private int getDroneUsedSpace(List<Dispatch> dispatchList) {
		return dispatchList.stream().map(Dispatch::getMedication).map(Medication::getWeight).mapToInt(Integer::intValue)
				.sum();
	}
}
