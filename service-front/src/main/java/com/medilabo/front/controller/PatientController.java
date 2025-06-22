package com.medilabo.front.controller;

import com.medilabo.front.bean.NoteBean;
import com.medilabo.front.bean.PatientBean;
import com.medilabo.front.bean.ReportBean;
import com.medilabo.front.dto.PatientDTO;
import com.medilabo.front.proxy.DiabetesRiskProxy;
import com.medilabo.front.proxy.NoteServiceProxy;
import com.medilabo.front.proxy.PatientServiceProxy;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@Component
public class PatientController {
    @Value("${server.redirection.host}")
    private String redirectionHost;

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
        model.addAttribute("pageTitle", "Liste des patients");
        model.addAttribute("patients", patientServiceProxy.getPatients());
        return "patient/index";
    }

    @GetMapping("/patients/inspect/{patientId}")
    public String patientInspectView(@PathVariable("patientId") Integer patientId, Model model) {
        PatientBean patient = this.getPatientById(patientId);
        if(patient == null) {
            return "patient/notfound";
        }

        List<NoteBean> patientNotes = noteServiceProxy.getPatientNotes(patientId);
        ReportBean patientReport = diabetesRiskProxy.getPatientDiabetesRiskReport(patientId);

        model.addAttribute("pageTitle", "Aperçu patient");
        model.addAttribute("patient", patient);
        model.addAttribute("notes", patientNotes);
        model.addAttribute("report", patientReport);
        return "patient/inspect";
    }

    @GetMapping("/patients/create")
    public String createPatientView(Model model) {
        model.addAttribute("pageTitle", "Créer un patient");
        model.addAttribute("patient", new PatientDTO());

        return "patient/create";
    }

    @PostMapping("/patients/create")
    public String createPatient(@Valid @ModelAttribute("patient") PatientDTO body, BindingResult result, Model model) {
        if(result.hasErrors()) {
            log.debug("Invalid request body for patient creation, reason : invalid field " + result.getFieldError().getField());

            model.addAttribute("pageTitle", "Créer un patient");
            model.addAttribute("patient", body);
            return "patient/create";
        }

        PatientBean createdBean = null;

        try {
            PatientBean bean = this.toBean(body);
            createdBean = patientServiceProxy.createPatient(bean);
        }
        catch(Exception ex) {
            log.debug("Failed to create patient, reason : " + ex.getMessage());

            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("pageTitle", "Créer un patient");
            model.addAttribute("patient", body);
            return "patient/create";
        }

        return "redirect:" + redirectionHost + "/patients/inspect/" + createdBean.getId().toString();
    }

    @GetMapping("/patients/update/{patientId}")
    public String updatePatientView(@PathVariable("patientId") Integer patientId, Model model) {
        PatientBean patient = this.getPatientById(patientId);
        if(patient == null) {
            return "patient/notfound";
        }

        model.addAttribute("pageTitle", "Modifier un patient");
        model.addAttribute("patient", patient);
        return "patient/update";
    }

    @PostMapping("/patients/update/{patientId}")
    public String updatePatient(@PathVariable("patientId") Integer patientId, @Valid @ModelAttribute("patient") PatientDTO body, BindingResult result, Model model) {
        PatientBean patient = this.getPatientById(patientId);
        if(patient == null) {
            return "patient/notfound";
        }

        body.setId(patientId);

        if(result.hasErrors()) {
            log.debug("Invalid request body for patient update, reason : invalid field " + result.getFieldError().getField());

            model.addAttribute("pageTitle", "Modifier un patient");
            model.addAttribute("patient", body);
            return "patient/update";
        }

        try {
            PatientBean bean = this.toBean(body);
            patientServiceProxy.patchPatient(patientId, bean);
        }
        catch(Exception ex) {
            log.debug("Failed to update patient, reason : " + ex.getMessage());

            model.addAttribute("pageTitle", "Modifier un patient");
            model.addAttribute("patient", body);
            model.addAttribute("errorMessage", ex.getMessage());
            return "patient/update";
        }

        return "redirect:" + redirectionHost + "/patients/inspect/" + patientId.toString();
    }

    @GetMapping("/patients/delete/{patientId}")
    public String deletePatient(@PathVariable("patientId") Integer patientId, Model model) {
        PatientBean patient = this.getPatientById(patientId);
        if(patient == null) {
            return "patient/notfound";
        }

        try {
            patientServiceProxy.deletePatient(patientId);
        }
        catch(Exception ex) {
            log.error("Failed to delete patient, reason : " + ex.getMessage());
        }

        return "redirect:" + redirectionHost + "/patients";
    }

    private PatientBean toBean(PatientDTO dao) {
        PatientBean bean = new PatientBean();

        bean.setFirstName(dao.getFirstName());
        bean.setLastName(dao.getLastName());
        bean.setBirthDate(dao.getBirthDate());
        bean.setGender(dao.getGender());
        bean.setAddress(dao.getAddress());
        bean.setPhoneNumber(dao.getPhoneNumber());

        return bean;
    }

    private PatientBean getPatientById(Integer patientId) {
        try {
            return patientServiceProxy.getPatientById(patientId);
        }
        catch(Exception ex) {
            log.debug("Failed to get patient by id " + patientId.toString() + ", reason : " + ex.getMessage());
        }

        return null;
    }
}
