package com.medilabo.diabetesrisk.exception;

public class PatientNotFoundException extends Exception {
    public PatientNotFoundException(Integer patientId) {
        super("Can not find patient with id : " + patientId.toString());
    }

    public PatientNotFoundException() {
        super("Can not find patient");
    }
}
