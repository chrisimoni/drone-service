package com.chrisreal.droneservicetask.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dispatch {

    @javax.persistence.Id
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "serial_no",nullable = false)
    private Drone drone;

    @ManyToOne
    @JoinColumn(name = "code",nullable = false)
    private Medication medication;

    private LocalDateTime timestamp;

}
