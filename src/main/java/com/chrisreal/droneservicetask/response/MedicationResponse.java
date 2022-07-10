package com.chrisreal.droneservicetask.response;

import java.util.List;

import com.chrisreal.droneservicetask.model.Medication;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedicationResponse extends BaseResponse{
    Medication medication;
    List<Medication> medications;
    Integer count;


    public MedicationResponse(String code, String message, Medication medication) {
        super(code, message);
        this.medication = medication;
    }

    public MedicationResponse(Medication medication) {
        this.medication = medication;
    }

    public MedicationResponse(String code, String message) {
        super(code, message);
    }

    public MedicationResponse(String code, String message, List<Medication> medications) {
        super(code, message);
        this.medications = medications;
        this.count = medications.size();
    }

}
