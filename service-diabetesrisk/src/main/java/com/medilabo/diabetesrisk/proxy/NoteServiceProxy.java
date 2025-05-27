package com.medilabo.diabetesrisk.proxy;

import com.medilabo.diabetesrisk.bean.NoteBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "service-note", url = "${proxy.service.note.url}")
public interface NoteServiceProxy {
    @GetMapping(value = "/patient-notes/{patientId}")
    public List<NoteBean> getPatientNotes(@PathVariable(name = "patientId") Integer patientId);
}
