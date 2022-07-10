package com.chrisreal.droneservicetask.validators;

import org.springframework.stereotype.Service;

import com.chrisreal.droneservicetask.dto.DroneDto;
import com.chrisreal.droneservicetask.exception.BadRequestException;

/**
 * Created by Athanasius.Lawrence on 26/04/2022.
 */
@Service
public class Validator {

    public void validateDrones(DroneDto dronesDto) throws BadRequestException {

        if (isNullOrEmptyString(dronesDto.getSerialNo())) {
            throw new BadRequestException("Serial Number is required");
        }

        if (dronesDto.getSerialNo().length() > 100) {
            throw new BadRequestException("Serial Number should not exceed 100 characters");
        }

        if (dronesDto.getBatteryCapacity() > 100 || dronesDto.getBatteryCapacity() < 0) {
            throw new BadRequestException("Battery capacity should be between 0 and 100");
        }

        if (isNullOrEmptyString(dronesDto.getModel())) {
            throw new BadRequestException("Drone model is required");
        }

        if (isNullOrEmptyString(dronesDto.getState())) {
            throw new BadRequestException("Drone state is required");
        }

        if (dronesDto.getBatteryCapacity() == null || dronesDto.getBatteryCapacity() < 1) {
            throw new BadRequestException("Battery capacity is required");
        }

        if (dronesDto.getWeightLimit() == null) {
            throw new BadRequestException("Weight limit is required");
        }

        if (dronesDto.getWeightLimit() > 500) {
            throw new BadRequestException("Weight limit should not exceed 500");
        }
    }

    public static boolean isNullOrEmptyString(String stringValue) {
        return (null == stringValue || stringValue.trim().equals(""));
    }

}

