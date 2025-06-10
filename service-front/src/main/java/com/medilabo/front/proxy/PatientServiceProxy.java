package com.medilabo.front.proxy;

import com.medilabo.front.bean.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "service-patient", url = "${proxy.service.patient.url}")
public interface PatientServiceProxy {
    @RequestMapping(method = RequestMethod.GET, value  = "/patients/{id}", consumes = "application/json")
    PatientBean getPatientById(@PathVariable("id") Integer id);

    @RequestMapping(method = RequestMethod.GET, value  = "/patients", consumes = "application/json")
    List<PatientBean> getPatients();

    @RequestMapping(method = RequestMethod.POST, value  = "/patients", consumes = "application/json")
    PatientBean createPatient(@RequestBody PatientBean patientBean);

    @RequestMapping(method = RequestMethod.PUT, value  = "/patients/{id}", consumes = "application/json")
    PatientBean patchPatient(@PathVariable("id") Integer id, @RequestBody PatientBean patientBean);

    @RequestMapping(method = RequestMethod.DELETE, value  = "/patients/{id}", consumes = "application/json")
    PatientBean deletePatient(@PathVariable("id") Integer id);
}
