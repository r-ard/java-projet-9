package com.medilabo.note.exception;

public class NoteNotFoundException extends Exception {
    public NoteNotFoundException(String id) {
        super("Can not find note with id '" + id + "'");
    }

    public NoteNotFoundException() {
        super("Can not find note");
    }
}
