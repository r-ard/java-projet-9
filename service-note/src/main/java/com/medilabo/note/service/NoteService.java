package com.medilabo.note.service;


import com.medilabo.note.dao.NoteDAO;
import com.medilabo.note.exception.NoteNotFoundException;
import com.medilabo.note.model.Note;
import com.medilabo.note.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<NoteDAO> getNotes() {
        Iterable<Note> notes = noteRepository.findAll();

        // Convert patients to dao
        List<NoteDAO> outNotes = new ArrayList<>();
        for(Note note : notes) {
            outNotes.add( toDAO(note) );
        }

        return outNotes;
    }

    public List<NoteDAO> getPatientNotes(Integer patientId) {
        Iterable<Note> notes = noteRepository.findAllByPatientIdOrderByDateDesc(patientId);

        // Convert patients to dao
        List<NoteDAO> outNotes = new ArrayList<>();
        for(Note note : notes) {
            outNotes.add( toDAO(note) );
        }

        return outNotes;
    }

    public NoteDAO getNoteById(String id) throws NoteNotFoundException {
        Note note = noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));
        return toDAO(note);
    }

    public NoteDAO createNote(NoteDAO dao) {
        Note entity = toEntity(dao);
        entity.setDate(LocalDate.now());
        entity = noteRepository.save(entity);
        return toDAO(entity);
    }

    public NoteDAO updateNote(NoteDAO dao) throws NoteNotFoundException {
        Note existingNote = noteRepository.findById(dao.getId()).orElseThrow(() -> new NoteNotFoundException(dao.getId()));

        existingNote.setPatientId(dao.getPatientId());
        existingNote.setContent(dao.getContent());
        existingNote.setDate(dao.getDate());

        Note updatedNote = noteRepository.save(existingNote);
        return toDAO(updatedNote);
    }

    public NoteDAO deleteNoteById(String id) throws NoteNotFoundException {
        NoteDAO outDao = this.getNoteById(id);
        noteRepository.deleteById(id);
        return outDao;
    }

    private static NoteDAO toDAO(Note note) {
        NoteDAO dao = new NoteDAO();

        dao.setId(note.getId());
        dao.setPatientId(note.getPatientId());
        dao.setContent(note.getContent());
        dao.setDate(note.getDate());

        return dao;
    }

    private static Note toEntity(NoteDAO dao) {
        Note entity = new Note();

        entity.setId(dao.getId());
        entity.setPatientId(dao.getPatientId());
        entity.setContent(dao.getContent());
        entity.setDate(dao.getDate());

        return entity;
    }
}
