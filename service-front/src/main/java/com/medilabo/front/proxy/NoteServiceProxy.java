package com.medilabo.front.proxy;

import com.medilabo.front.bean.NoteBean;
import com.medilabo.front.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "service-note", url = "${proxy.service.note.url}", configuration = FeignClientConfig.class)
public interface NoteServiceProxy {
    @RequestMapping(method = RequestMethod.GET, value  = "/notes/{id}", consumes = "application/json")
    public NoteBean getNoteById(@PathVariable(name = "id") String id);

    @RequestMapping(method = RequestMethod.POST, value  = "/notes", consumes = "application/json")
    public NoteBean createNode(@RequestBody NoteBean note);

    @RequestMapping(method = RequestMethod.PUT, value  = "/notes/{id}", consumes = "application/json")
    public NoteBean updateNote(@PathVariable(name = "id") String id, @RequestBody NoteBean note);

    @RequestMapping(method = RequestMethod.DELETE, value  = "/notes/{id}", consumes = "application/json")
    public NoteBean deleteNote(@PathVariable(name = "id") String id);

    @RequestMapping(method = RequestMethod.GET, value  = "/patient-notes/{patientId}", consumes = "application/json")
    public List<NoteBean> getPatientNotes(@PathVariable(name = "patientId") Integer patientId);
}
