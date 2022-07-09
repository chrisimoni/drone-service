package com.chrisreal.droneservicetask.enums;

import com.chrisreal.droneservicetask.exception.BadRequestException;

public enum DroneModel {
	LIGHT_WEIGHT,
	  MIDDLE_WEIGHT,
	  CRUISER_WEIGHT,
	  HEAVY_WEIGHT;

	  public static DroneModel get(String model) throws BadRequestException {
	    DroneModel value;
	    try {
	      value = DroneModel.valueOf(model);
	    }
	    catch(IllegalArgumentException | NullPointerException e){
	      throw new BadRequestException(
	          "Invalid value for Model. Valid values are: LIGHT_WEIGHT, MIDDLE_WEIGHT, CRUISER_WEIGHT or HEAVY_WEIGHT");
	    }
	    return value;
	  }
}
