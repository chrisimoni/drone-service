package com.chrisreal.droneservicetask.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.chrisreal.droneservicetask.enums.DroneModel;
import com.chrisreal.droneservicetask.enums.DroneState;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Drone {
	@javax.persistence.Id
    @Column(name = "serial_no", length = 100)
    private String serialNo;
    @Enumerated(EnumType.ORDINAL)
    private DroneModel model;
    @Column(name = "weight_limit", columnDefinition = "VARCHAR(6) NOT NULL")
    private Long weightLimit;
    private Double batteryCapacity;
    @Enumerated(EnumType.ORDINAL)
    private DroneState state;
}
