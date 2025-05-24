package com.medilabo.patient.service;


import com.medilabo.patient.dao.PatientDAO;
import com.medilabo.patient.exception.PatientNotFoundException;
import com.medilabo.patient.model.Patient;
import com.medilabo.patient.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientDAO> getPatients() {
        Iterable<Patient> patients = patientRepository.findAll();

        // Convert patients to dao
        List<PatientDAO> outPatients = new ArrayList<>();
        for(Patient patient : patients) {
            outPatients.add( toDAO(patient) );
        }

        return outPatients;
    }

    public PatientDAO getPatientById(Integer id) throws PatientNotFoundException {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
        return toDAO(patient);
    }

    public PatientDAO createPatient(PatientDAO dao) {
        Patient entity = toEntity(dao);
        entity = patientRepository.save(entity);
        return toDAO(entity);
    }

    public PatientDAO updatePatient(PatientDAO dao) throws PatientNotFoundException {
        Patient existingPatient = patientRepository.findById(dao.getId()).orElseThrow(() -> new PatientNotFoundException(dao.getId()));

        existingPatient.setFirstName(dao.getFirstName());
        existingPatient.setLastName(dao.getLastName());
        existingPatient.setBirthDate(dao.getBirthDate());
        existingPatient.setGender(dao.getGender());
        existingPatient.setAddress(dao.getAddress());
        existingPatient.setPhoneNumber(dao.getPhoneNumber());

        Patient updatedPatient = patientRepository.save(existingPatient);
        return toDAO(updatedPatient);
    }

    public PatientDAO deletePatientById(Integer id) throws PatientNotFoundException {
        PatientDAO outDao = this.getPatientById(id);
        patientRepository.deleteById(id);
        return outDao;
    }

    private static PatientDAO toDAO(Patient patient) {
        PatientDAO dao = new PatientDAO();

        dao.setId(patient.getId());
        dao.setLastName(patient.getLastName());
        dao.setFirstName(patient.getFirstName());
        dao.setBirthDate(patient.getBirthDate());
        dao.setGender(patient.getGender());
        dao.setAddress(patient.getAddress());
        dao.setPhoneNumber(patient.getPhoneNumber());

        return dao;
    }

    private static Patient toEntity(PatientDAO dao) {
        Patient entity = new Patient();

        entity.setId(dao.getId());
        entity.setLastName(dao.getLastName());
        entity.setFirstName(dao.getFirstName());
        entity.setBirthDate(dao.getBirthDate());
        entity.setGender(dao.getGender());
        entity.setAddress(dao.getAddress());
        entity.setPhoneNumber(dao.getPhoneNumber());

        return entity;
    }
}
