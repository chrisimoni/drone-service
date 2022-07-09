package com.chrisreal.droneservicetask.enums;

import com.chrisreal.droneservicetask.exception.BadRequestException;

public enum DroneState {
	IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING;

	public static DroneState get(String state) throws BadRequestException {
		DroneState value;
		try {
			value = DroneState.valueOf(state);
		} catch (IllegalArgumentException e) {
			throw new BadRequestException(
					"Invalid value for Drone State. Valid states are: IDLE, LOADING, LOADED, DELIVERING, DELIVERED or RETURNING");
		}
		return value;
	}
}
