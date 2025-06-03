package com.medilabo.front.controller;

import com.medilabo.front.bean.PatientBean;
import com.medilabo.front.dao.PatientDAO;
import com.medilabo.front.proxy.DiabetesRiskProxy;
import com.medilabo.front.proxy.NoteServiceProxy;
import com.medilabo.front.proxy.PatientServiceProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class PatientController {
    private final PatientServiceProxy patientServiceProxy;

    private final DiabetesRiskProxy diabetesRiskProxy;

    private final NoteServiceProxy noteServiceProxy;

    public PatientController(PatientServiceProxy patientServiceProxy, DiabetesRiskProxy diabetesRiskProxy, NoteServiceProxy noteServiceProxy) {
        this.patientServiceProxy = patientServiceProxy;
        this.diabetesRiskProxy = diabetesRiskProxy;
        this.noteServiceProxy = noteServiceProxy;
    }

    @GetMapping("/patients")
    public String patientsView(Model model) {
        model.addAttribute("pageTitle", "Aperçu patients");
        model.addAttribute("patients", patientServiceProxy.getPatients());
        return "patient/index";
    }

    @GetMapping("/patients/inspect/{patientId}")
    public String patientInspectView(@PathVariable("patientId") Integer patientId, Model model) {
        PatientBean patient = patientServiceProxy.getPatientById(patientId);
        if(patient != null) {
            // TODO : handle not found
        }

        model.addAttribute("pageTitle", "Aperçu patient");
        model.addAttribute("patient", patient);
        return "patient/inspect";
    }

    @GetMapping("/patients/create")
    public String createPatientNoteView(Model model) {
        return "patient/create";
    }

    @PostMapping("/patients/create")
    public String createPatient(@RequestBody PatientDAO note, BindingResult result, Model model) {
        return "";
    }

    @GetMapping("/patients/update/{patientId}")
    public String updatePatientView(@PathVariable("patientId") Integer patientId, Model model) {
        PatientBean patient = patientServiceProxy.getPatientById(patientId);
        if(patient != null) {
            // TODO : handle not found
        }

        model.addAttribute("pageTitle", "Modifier un patient");
        model.addAttribute("patient", patient);
        return "patient/update";
    }

    @PatchMapping("/patients/update/{patientId}")
    public String updatePatient(@PathVariable("patientId") Integer patientId, @RequestBody PatientDAO note, BindingResult result, Model model) {
        return "";
    }
}
