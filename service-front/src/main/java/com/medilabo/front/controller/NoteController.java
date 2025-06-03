package com.medilabo.front.controller;

import com.medilabo.front.dao.NoteDAO;
import com.medilabo.front.proxy.NoteServiceProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class NoteController {
    private final NoteServiceProxy noteServiceProxy;

    public NoteController(NoteServiceProxy noteServiceProxy) {
        this.noteServiceProxy = noteServiceProxy;
    }

    @GetMapping("/notes/create/{patientId}")
    public String createPatientNoteView(@PathVariable("patientId") Integer patientId, Model model) {
        return "note/create";
    }

    @PostMapping("/notes/create/{patientId}")
    public String createPatientNote(@PathVariable("patientId") Integer patientId, @RequestBody NoteDAO note, BindingResult result, Model model) {
        return "";
    }

    @GetMapping("/notes/update/{noteId}")
    public String updateNoteView(@PathVariable("noteId") Integer noteId) {
        return "note/update";
    }

    @PatchMapping("/notes/update/{noteId}")
    public String updateNote(@PathVariable("noteId") Integer noteId, @RequestBody NoteDAO note, BindingResult result, Model model) {
        return "";
    }
}
