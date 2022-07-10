package com.chrisreal.droneservicetask.dto;

import java.time.LocalDateTime;

import com.chrisreal.droneservicetask.model.Dispatch;
import com.chrisreal.droneservicetask.model.Drone;
import com.chrisreal.droneservicetask.model.Medication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DispatchDto {

  private String id;

  private String droneSerialNo;

  private String medicationCode;

  private LocalDateTime timestamp;

  private Drone drone;

  private Medication medication;
  public Dispatch toModel() {
    return new Dispatch(this.getId(), this.getDrone(), this.getMedication(), this.getTimestamp());
  }

  public DispatchDto(String droneSerialNo, String medicationCode) {
    this.droneSerialNo = droneSerialNo;
    this.medicationCode = medicationCode;
  }
}
