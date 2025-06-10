package com.medilabo.front.controller;

import com.medilabo.front.bean.NoteBean;
import com.medilabo.front.bean.PatientBean;
import com.medilabo.front.dao.NoteDAO;
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

@Slf4j
@Controller
@Component
public class NoteController {
    @Value("${server.redirection.host}")
    private String redirectionHost;

    private final NoteServiceProxy noteServiceProxy;

    private final PatientServiceProxy patientServiceProxy;

    public NoteController(NoteServiceProxy noteServiceProxy, PatientServiceProxy patientServiceProxy) {
        this.noteServiceProxy = noteServiceProxy;
        this.patientServiceProxy = patientServiceProxy;
    }

    @GetMapping("/notes/create/{patientId}")
    public String createPatientNoteView(@PathVariable("patientId") Integer patientId, Model model) {
        PatientBean patient = this.getPatientById(patientId);
        if(patient == null) {
            return "patient/notfound";
        }

        model.addAttribute("pageTitle", "Créer une note");
        model.addAttribute("patient", patient);
        model.addAttribute("note", new NoteDAO());

        return "note/create";
    }

    @PostMapping("/notes/create/{patientId}")
    public String createPatientNote(@PathVariable("patientId") Integer patientId, @Valid @ModelAttribute("note") NoteDAO body, BindingResult result, Model model) {
        PatientBean patient = this.getPatientById(patientId);
        if(patient == null) {
            return "patient/notfound";
        }

        if(result.hasErrors()) {
            log.debug("Invalid request body for note creation, reason : invalid field " + result.getFieldError().getField());

            model.addAttribute("pageTitle", "Créer une note");
            model.addAttribute("patient", patient);
            model.addAttribute("note", body);
            return "note/create";
        }

        try {
            NoteBean bean = this.toBean(body);
            bean.setPatientId(patientId);
            noteServiceProxy.createNode(bean);
        }
        catch(Exception ex) {
            log.debug("Failed to create note to patient " + patientId.toString() + ", reason : " + ex.getMessage());

            model.addAttribute("pageTitle", "Créer une note");
            model.addAttribute("patient", patient);
            model.addAttribute("note", body);
            model.addAttribute("errorMessage", ex.getMessage());
            return "note/create";
        }

        return "redirect:" + redirectionHost + "/service-front/patients/inspect/" + patientId.toString();
    }

    @GetMapping("/notes/update/{noteId}")
    public String updateNoteView(@PathVariable("noteId") String noteId, Model model) {
        NoteBean note = this.getNoteById(noteId);
        if(note == null) {
            return "note/notfound";
        }

        model.addAttribute("pageTitle", "Mettre à jour une note");
        model.addAttribute("note", note);
        model.addAttribute("patientId", note.getPatientId());

        return "note/update";
    }

    @PostMapping("/notes/update/{noteId}")
    public String updateNote(@PathVariable("noteId") String noteId, @Valid @ModelAttribute("note") NoteDAO body, BindingResult result, Model model) {
        NoteBean note = this.getNoteById(noteId);
        if(note == null) {
            return "note/notfound";
        }

        if(result.hasErrors()) {
            log.debug("Invalid request body for note update, reason : invalid field " + result.getFieldError().getField());

            model.addAttribute("pageTitle", "Mettre à jour une note");
            model.addAttribute("note", body);
            model.addAttribute("patientId", note.getPatientId());
            return "note/update";
        }

        try {
            NoteBean bean = this.toBean(body);
            noteServiceProxy.updateNote(noteId, bean);
        }
        catch(Exception ex) {
            log.debug("Failed to update note, reason : " + ex.getMessage());

            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("pageTitle", "Mettre à jour une note");
            model.addAttribute("note", body);
            model.addAttribute("patientId", note.getPatientId());
            return "note/update";
        }

        return "redirect:" + redirectionHost + "/service-front/patients/inspect/" + note.getPatientId().toString();
    }

    @GetMapping("/notes/delete/{noteId}")
    public String deleteNote(@PathVariable("noteId") String noteId, Model model) {
        NoteBean note = this.getNoteById(noteId);
        if(note == null) {
            return "note/notfound";
        }

        noteServiceProxy.deleteNote(noteId);

        return "redirect:" + redirectionHost + "/service-front/patients/inspect/" + note.getPatientId().toString();
    }

    private NoteBean toBean(NoteDAO dao) {
        NoteBean bean = new NoteBean();

        bean.setContent(dao.getContent());

        if(dao.getDate() != null) {
            bean.setDate(dao.getDate());
        }

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

    private NoteBean getNoteById(String noteId) {
        try {
            return noteServiceProxy.getNoteById(noteId);
        }
        catch(Exception ex) {
            log.debug("Failed to get note by id " + noteId + ", reason : " + ex.getMessage());
        }

        return null;
    }
}
