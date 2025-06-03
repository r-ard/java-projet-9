package com.medilabo.front.proxy;

import com.medilabo.front.bean.NoteBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "service-note", url = "${proxy.service.note.url}")
public interface NoteServiceProxy {
    @GetMapping(value = "/notes/{id}")
    public NoteBean getNoteById(@PathVariable(name = "id") Integer id);

    @PostMapping(value = "/notes")
    public NoteBean createNode(@RequestBody NoteBean note);

    @PatchMapping(value = "/notes/{id}")
    public NoteBean updateNote(@PathVariable(name = "id") Integer id, @RequestBody NoteBean note);

    @DeleteMapping(value = "/notes/{id}")
    public NoteBean deleteNote(@PathVariable(name = "id") Integer id);

    @GetMapping(value = "/patient-notes/{patientId}")
    public List<NoteBean> getPatientNotes(@PathVariable(name = "patientId") Integer patientId);
}
