package com.medilabo.patient.controller;

import com.medilabo.patient.dao.PatientDAO;
import com.medilabo.patient.exception.PatientNotFoundException;
import com.medilabo.patient.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("Status : ok");
    }

    @GetMapping("/patients")
    public ResponseEntity<List<PatientDAO>> getPatients() {
        log.info("Received request : get patients");

        List<PatientDAO> patients = patientService.getPatients();
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<PatientDAO> getPatientById(@PathVariable("id") Integer id) {
        log.info("Received request : get patient by id");

        try {
            PatientDAO patient = patientService.getPatientById(id);
            log.debug("Patient successfully found, id : " + id);
            return ResponseEntity.ok(patient);
        }
        catch(PatientNotFoundException ex) {
            log.error("Failed to find patient, reason : " + ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/patients")
    public ResponseEntity<PatientDAO> createPatient(@RequestBody PatientDAO patient, BindingResult result) {
        log.info("Received request : create patient");

        if(result.hasErrors()) {
            log.debug("Invalid request body for patient creation, reason : invalid field " + result.getFieldError().getField());
            return ResponseEntity.badRequest().build();
        }

        try {
            PatientDAO createdPatient = patientService.createPatient(patient);
            log.debug("Patient successfully created with id : " + createdPatient.getId());
            return ResponseEntity.ok(createdPatient);
        }
        catch(Exception ex) {
            log.error("Failed to create patient, reason : " + ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping("/patients/{id}")
    public ResponseEntity<PatientDAO> updatePatient(@PathVariable("id") Integer id, @RequestBody PatientDAO patient, BindingResult result) {
        log.info("Received request : update patient");

        if(result.hasErrors()) {
            log.debug("Invalid request body for patient update, reason : invalid field " + result.getFieldError().getField());
            return ResponseEntity.badRequest().build();
        }

        try {
            patient.setId(id);
            PatientDAO updatePatient = patientService.updatePatient(patient);
            log.debug("Patient successfully updated with id : " + id);
            return ResponseEntity.ok(updatePatient);
        }
        catch(PatientNotFoundException ex) {
            log.error("Failed to update patient, reason : " + ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/patients/{id}")
    public ResponseEntity<PatientDAO> deletePatient(@PathVariable("id") Integer id) {
        log.info("Received request : delete patient");

        try {
            PatientDAO patient = patientService.deletePatientById(id);
            log.debug("Patient successfully deleted with id : " + id);
            return ResponseEntity.ok(patient);
        }
        catch(PatientNotFoundException ex) {
            log.error("Failed to delete patient, reason : " + ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
