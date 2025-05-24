package com.medilabo.note.controller;

import com.medilabo.note.dao.NoteDAO;
import com.medilabo.note.exception.NoteNotFoundException;
import com.medilabo.note.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("Status : ok");
    }

    @GetMapping("/notes")
    public ResponseEntity<List<NoteDAO>> getNotes() {
        log.info("Received request : get notes");

        List<NoteDAO> notes = noteService.getNotes();
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/patient-notes/{patientId}")
    public ResponseEntity<List<NoteDAO>> getPatientNotes(@PathVariable("patientId") Integer patientId) {
        log.info("Received request : get patient notes");

        List<NoteDAO> notes = noteService.getPatientNotes(patientId);
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/notes/{id}")
    public ResponseEntity<NoteDAO> getNoteById(@PathVariable("id") String id) {
        log.info("Received request : get note by id");

        try {
            NoteDAO note = noteService.getNoteById(id);
            log.debug("Note successfully found, id : " + id);
            return ResponseEntity.ok(note);
        }
        catch(NoteNotFoundException ex) {
            log.error("Failed to find note, reason : " + ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/notes")
    public ResponseEntity<NoteDAO> createNote(@RequestBody NoteDAO note, BindingResult result) {
        log.info("Received request : create note");

        if(result.hasErrors()) {
            log.debug("Invalid request body for note creation, reason : invalid field " + result.getFieldError().getField());
            return ResponseEntity.badRequest().build();
        }

        try {
            NoteDAO createdNote = noteService.createNote(note);
            log.debug("Note successfully created with id : " + createdNote.getId());
            return ResponseEntity.ok(createdNote);
        }
        catch(Exception ex) {
            log.error("Failed to create note, reason : " + ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping("/notes/{id}")
    public ResponseEntity<NoteDAO> updateNote(@PathVariable("id") String id, @RequestBody NoteDAO note, BindingResult result) {
        log.info("Received request : update note");

        if(result.hasErrors()) {
            log.debug("Invalid request body for note update, reason : invalid field " + result.getFieldError().getField());
            return ResponseEntity.badRequest().build();
        }

        try {
            note.setId(id);
            NoteDAO updatedNote = noteService.updateNote(note);
            log.debug("Note successfully updated with id : " + id);
            return ResponseEntity.ok(updatedNote);
        }
        catch(NoteNotFoundException ex) {
            log.error("Failed to update note, reason : " + ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/notes/{id}")
    public ResponseEntity<NoteDAO> deleteNote(@PathVariable("id") String id) {
        log.info("Received request : delete note");

        try {
            NoteDAO note = noteService.deleteNoteById(id);
            log.debug("Note successfully deleted with id : " + id);
            return ResponseEntity.ok(note);
        }
        catch(NoteNotFoundException ex) {
            log.error("Failed to delete note, reason : " + ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
