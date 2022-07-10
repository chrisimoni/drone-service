package com.chrisreal.droneservicetask.validators;

import org.springframework.stereotype.Service;

import com.chrisreal.droneservicetask.dto.DroneDto;
import com.chrisreal.droneservicetask.dto.MedicationDto;
import com.chrisreal.droneservicetask.exception.BadRequestException;

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

    public void validateMedication(MedicationDto medicationDto) throws BadRequestException {

        if (isNullOrEmptyString(medicationDto.getCode())) {
            throw new BadRequestException("Code is required");
        }

        // check for special chars
        for(int i =0; i < medicationDto.getCode().length(); i++){
            int c = medicationDto.getCode().charAt(i);
            if(!isUppercaseLetter(c) && medicationDto.getCode().charAt(i) != '_' && !isDigit(c)){
                throw new BadRequestException("Code should be only Uppercase, underscore and numbers");
            }
        }

        if (isNullOrEmptyString(medicationDto.getName())) {
            throw new BadRequestException("Name is required");
        }

        // name should be only letters, numbers, - and _
        for(int i =0; i < medicationDto.getName().length(); i++){
            int c = medicationDto.getName().charAt(i);
            if(!isLowerCase(c) && !isUppercaseLetter(c) && c != (int)'_' && c != (int)'-'){
                throw new BadRequestException("Name should be only letters, numbers, - and _");
            }
        }

        if (medicationDto.getWeight() == null) {
            throw new BadRequestException("Weight is required");
        }
    }

    public static boolean isNullOrEmptyString(String stringValue) {
        return (null == stringValue || stringValue.trim().equals(""));
    }

    private boolean isLowerCase(int c) {
        return (c >= 97 && c <= 122);
    }

    private boolean isUppercaseLetter(int c) {
        return (c >= 65 && c <= 90);
    }

    private boolean isDigit(int c) {
        return (c >= 48 && c <= 57);
    }

}

