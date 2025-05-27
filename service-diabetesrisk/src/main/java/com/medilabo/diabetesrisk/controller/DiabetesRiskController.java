package com.medilabo.diabetesrisk.controller;

import com.medilabo.diabetesrisk.dao.ReportDAO;
import com.medilabo.diabetesrisk.exception.PatientNotFoundException;
import com.medilabo.diabetesrisk.service.DiabetesRiskService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DiabetesRiskController {
    private final DiabetesRiskService diabetesRiskService;

    public DiabetesRiskController(DiabetesRiskService diabetesRiskService) {
        this.diabetesRiskService = diabetesRiskService;
    }

    @GetMapping("/diabetes-risk/{patientId}")
    public ResponseEntity<ReportDAO> getPatientDiabetesRiskReport(@PathVariable("patientId") Integer patientId) {
        try {
            ReportDAO report = this.diabetesRiskService.getPatientReport(patientId);
            return ResponseEntity.ok().body(report);
        }
        catch(PatientNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
