package com.chrisreal.droneservicetask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chrisreal.droneservicetask.dto.DroneDto;
import com.chrisreal.droneservicetask.exception.BadRequestException;
import com.chrisreal.droneservicetask.exception.DuplicateException;
import com.chrisreal.droneservicetask.exception.NotFoundException;
import com.chrisreal.droneservicetask.response.DroneResponse;
import com.chrisreal.droneservicetask.service.DroneService;

@RestController
@RequestMapping("/drones")
public class DroneController {
	@Autowired
	  DroneService droneService;

	  @PostMapping(produces = "application/json")
	  public ResponseEntity<DroneResponse> postAvailableDroneForLoading(@RequestBody DroneDto droneDto) {
	    try{
	      DroneResponse droneResponse = droneService.registerDrone(droneDto);
	      return new ResponseEntity<>(droneResponse, HttpStatus.CREATED);
	    }catch (DuplicateException e){
	      return new ResponseEntity<>(new DroneResponse(e.getStatusCode(),e.getMessage()), HttpStatus.CONFLICT);
	    }catch (BadRequestException e){
	      return new ResponseEntity<>(new DroneResponse(e.getStatusCode(),e.getMessage()), HttpStatus.BAD_REQUEST);
	    }
	  }

	  @GetMapping(path= "/model/{model}", produces = "application/json")
	  public ResponseEntity<DroneResponse> getDronesByModel(@PathVariable("model") String model) {
	    try{
	      return ResponseEntity.ok(droneService.getDroneByModel(model));
	    }
	    catch (BadRequestException e){
	      return new ResponseEntity<>(new DroneResponse(e.getStatusCode(),e.getMessage()), HttpStatus.BAD_REQUEST);
	    }
	    catch (NotFoundException e){
	      return new ResponseEntity<>(new DroneResponse(e.getStatusCode(),e.getMessage()), HttpStatus.NOT_FOUND);
	    }
	  }

	  @GetMapping(path= "/state/{state}", produces = "application/json")
	  public ResponseEntity<DroneResponse> getDronesByState(@PathVariable("state") String state) {
	    try{
	      return ResponseEntity.ok(droneService.getDronesByState(state));
	    }
	    catch (BadRequestException e){
	      return new ResponseEntity<>(new DroneResponse(e.getStatusCode(),e.getMessage()), HttpStatus.BAD_REQUEST);
	    }
	    catch (NotFoundException e){
	      return new ResponseEntity<>(new DroneResponse(e.getStatusCode(),e.getMessage()), HttpStatus.NOT_FOUND);
	    }
	  }

	  @GetMapping(path= "/idle", produces = "application/json")
	  public ResponseEntity<DroneResponse> getDronesByState() {
	    try{
	      return ResponseEntity.ok(droneService.getIdleDrones());
	    }
	    catch (NotFoundException e){
	      return new ResponseEntity<>(new DroneResponse(e.getStatusCode(),e.getMessage()), HttpStatus.NOT_FOUND);
	    }
	  }
}
