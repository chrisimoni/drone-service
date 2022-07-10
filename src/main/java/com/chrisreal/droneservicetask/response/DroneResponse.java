package com.chrisreal.droneservicetask.response;

import java.util.List;

import com.chrisreal.droneservicetask.model.Drone;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DroneResponse extends BaseResponse {
	Drone drone;
	List<Drone> drones;
	Integer count;

	public Integer getCount() {
		return count;
	}

	public DroneResponse(String code, String message, Drone drone) {
		super(code, message);
		this.drone = drone;
	}

	public DroneResponse(Drone drone) {
		this.drone = drone;
	}

	public DroneResponse(String code, String message) {
		super(code, message);
	}

	public DroneResponse(String code, String message, List<Drone> drones) {
		super(code, message);
		this.drones = drones;
		this.count = drones.size();
	}
}
