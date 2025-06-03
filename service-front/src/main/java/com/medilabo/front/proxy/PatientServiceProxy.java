package com.medilabo.front.proxy;

import com.medilabo.front.bean.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "service-patient", url = "${proxy.service.patient.url}")
public interface PatientServiceProxy {
    @GetMapping(value = "/patients/{id}")
    PatientBean getPatientById(@PathVariable("id") Integer id);

    @GetMapping(value = "/patients")
    List<PatientBean> getPatients();

    @PostMapping(value = "/patients")
    PatientBean createPatient(@RequestBody PatientBean patientBean);

    @PatchMapping(value = "/patients/{id}")
    PatientBean patchPatient(@PathVariable("id") Integer id, @RequestBody PatientBean patientBean);

    @DeleteMapping(value = "/patients/{id}")
    PatientBean deletePatient(@PathVariable("id") Integer id);
}
