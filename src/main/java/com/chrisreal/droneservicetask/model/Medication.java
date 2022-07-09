package com.chrisreal.droneservicetask.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medication {
	@javax.persistence.Id
    @Column(name = "code")
    private String code;
    @Column(name = "weight")
    private Integer weight;
    @Column(name = "name")
    private String name;
    @Column(name = "image")
    private String image;
    private Boolean status;
}
