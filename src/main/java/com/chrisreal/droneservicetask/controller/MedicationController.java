package com.chrisreal.droneservicetask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chrisreal.droneservicetask.dto.MedicationDto;
import com.chrisreal.droneservicetask.exception.BadRequestException;
import com.chrisreal.droneservicetask.exception.DuplicateException;
import com.chrisreal.droneservicetask.exception.NotFoundException;
import com.chrisreal.droneservicetask.response.MedicationResponse;
import com.chrisreal.droneservicetask.service.MedicationService;

@RestController
@RequestMapping("/medication")
public class MedicationController {

  @Autowired
  MedicationService medicationService;

  @PostMapping(produces = "application/json")
  public ResponseEntity<MedicationResponse> postMedication(@RequestBody MedicationDto medicationDto) {
    try{
      MedicationResponse medicationResponse = medicationService.saveMedication(medicationDto);
      return new ResponseEntity<>(medicationResponse, HttpStatus.CREATED);
    }catch (DuplicateException e){
      return new ResponseEntity<>(new MedicationResponse(e.getStatusCode(),e.getMessage()), HttpStatus.CONFLICT);
    }catch (BadRequestException e){
      return new ResponseEntity<>(new MedicationResponse(e.getStatusCode(),e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping(produces = "application/json")
  public ResponseEntity<MedicationResponse> retrieveAllMedications() {
    try{
      return ResponseEntity.ok(medicationService.retrieveAllMedications());
    }
    catch (NotFoundException e){
      return new ResponseEntity<>(new MedicationResponse(e.getStatusCode(),e.getMessage()), HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping(path= "/active", produces = "application/json")
  public ResponseEntity<MedicationResponse> retrieveActiveMedication() {
    try{
      return ResponseEntity.ok(medicationService.retrieveAllMedicationsByStatus(true));
    }
    catch (NotFoundException e){
      return new ResponseEntity<>(new MedicationResponse(e.getStatusCode(),e.getMessage()), HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping(path= "/{code}", produces = "application/json")
  public ResponseEntity<MedicationResponse> findMedication(@PathVariable("code") String code) {
    try{
      return ResponseEntity.ok(medicationService.getMedicationsByCode(code));
    }
    catch (NotFoundException e){
      return new ResponseEntity<>(new MedicationResponse(e.getStatusCode(),e.getMessage()), HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping(path= "/disable/{code}", produces = "application/json")
  public ResponseEntity<MedicationResponse> disableMedication(@PathVariable("code") String code) {
    try{
      return ResponseEntity.ok(medicationService.disableMedicationByCode(code));
    }
    catch (NotFoundException e){
      return new ResponseEntity<>(new MedicationResponse(e.getStatusCode(),e.getMessage()), HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping(path= "/enable/{code}", produces = "application/json")
  public ResponseEntity<MedicationResponse> enableMedication(@PathVariable("code") String code) {
    try{
      return ResponseEntity.ok(medicationService.enableMedicationByCode(code));
    }
    catch (NotFoundException e){
      return new ResponseEntity<>(new MedicationResponse(e.getStatusCode(),e.getMessage()), HttpStatus.NOT_FOUND);
    }
  }

}
