package com.chrisreal.droneservicetask.dto;

import com.chrisreal.droneservicetask.enums.DroneModel;
import com.chrisreal.droneservicetask.enums.DroneState;
import com.chrisreal.droneservicetask.exception.BadRequestException;
import com.chrisreal.droneservicetask.model.Drone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneDto {
	private String serialNo;
	private String model;
	private Long weightLimit;
	private Double batteryCapacity;
	private String state;

	public Drone toModel() throws BadRequestException {
		return new Drone(this.getSerialNo(), DroneModel.get(this.getModel().toUpperCase()), this.getWeightLimit(),
				this.getBatteryCapacity(), DroneState.get(this.getState().toUpperCase()));
	}
}
