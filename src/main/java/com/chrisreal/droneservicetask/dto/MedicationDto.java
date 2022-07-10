package com.chrisreal.droneservicetask.dto;

import com.chrisreal.droneservicetask.model.Medication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicationDto {

  private String code;
  private Integer weight;

  private String name;

  private String image;

  private Boolean status;

  public MedicationDto(String code, Integer weight, String name, String image) {
    this.code = code;
    this.weight = weight;
    this.name = name;
    this.image = image;
  }

  public Medication toModel() {
    return new Medication(this.getCode(), this.getWeight(), this.getName(), this.getImage(), true);
  }

}
