package com.medilabo.patient.exception;

public class PatientNotFoundException extends Exception {
    public PatientNotFoundException(Integer id) {
        super("Can not find patient with id '" + id.toString() + "'");
    }

    public PatientNotFoundException() {
        super("Can not find patient");
    }
}
