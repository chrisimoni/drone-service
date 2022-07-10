package com.chrisreal.droneservicetask.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chrisreal.droneservicetask.dto.MedicationDto;
import com.chrisreal.droneservicetask.exception.BadRequestException;
import com.chrisreal.droneservicetask.exception.DuplicateException;
import com.chrisreal.droneservicetask.exception.NotFoundException;
import com.chrisreal.droneservicetask.model.Medication;
import com.chrisreal.droneservicetask.repository.MedicationRepository;
import com.chrisreal.droneservicetask.response.MedicationResponse;
import com.chrisreal.droneservicetask.validators.Validator;

@Service
public class MedicationService {
	@Autowired
	MedicationRepository medicationRepository;

	@Autowired
	Validator validators;

	public MedicationResponse saveMedication(MedicationDto medicationDto)
			throws BadRequestException, DuplicateException {
		validators.validateMedication(medicationDto);
		Optional<Medication> medication = medicationRepository.findById(medicationDto.getCode());
		if (medication.isEmpty()) {
			medicationRepository.save(medicationDto.toModel());
		} else {
			throw new DuplicateException("Record already exists with the submitted code.");
		}
		return new MedicationResponse("200", "Medication added successfully.");
	}

	public MedicationResponse retrieveAllMedications() throws NotFoundException {
		List<Medication> medicationList = medicationRepository.findAll();
		if (medicationList.size() == 0)
			throw new NotFoundException("No records found");

		return new MedicationResponse("200", "Medications retrieved successfully.", medicationList);
	}

	public MedicationResponse retrieveAllMedicationsByStatus(Boolean status) throws NotFoundException {
		List<Medication> medicationList = medicationRepository.findByStatus(status);
		if (medicationList.size() == 0)
			throw new NotFoundException("No records found");
		
		return new MedicationResponse("200", "Medications retrieved successfully.", medicationList);
	}

	public MedicationResponse getMedicationsByCode(String code) throws NotFoundException {
		Optional<Medication> medication = medicationRepository.findById(code);
		if (medication.isEmpty())
			throw new NotFoundException("No record found for " + code);
		
		return new MedicationResponse("200", "Medications retrieved successfully.", medication.get());
	}

	public MedicationResponse getMedicationByName(String name) throws NotFoundException {
		List<Medication> medications = medicationRepository.findByName(name);
		if (medications.isEmpty())
			throw new NotFoundException("No record found for " + name);
		
		return new MedicationResponse("200", "Medication retrieved successfully.", medications);
	}

	public MedicationResponse disableMedicationByCode(String code) throws NotFoundException {
		Optional<Medication> medication = medicationRepository.findById(code);
		if (medication.isEmpty())
			throw new NotFoundException("No record found for " + code);
		if (!medication.get().getStatus()) {
			return new MedicationResponse("200", "Medication is already disabled.", medication.get());
		}
		
		medication.get().setStatus(false);
		medicationRepository.save(medication.get());
		return new MedicationResponse("200", "Medication disabled successfully.", medication.get());
	}

	public MedicationResponse enableMedicationByCode(String code) throws NotFoundException {
		Optional<Medication> medication = medicationRepository.findById(code);
		if (medication.isEmpty())
			throw new NotFoundException("No record found for " + code);
		if (medication.get().getStatus()) {
			return new MedicationResponse("200", "Medication is already enabled.", medication.get());
		}
		
		medication.get().setStatus(true);
		medicationRepository.save(medication.get());
		return new MedicationResponse("200", "Medication enabled successfully.", medication.get());
	}
}
