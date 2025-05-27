package com.medilabo.diabetesrisk.proxy;

import com.medilabo.diabetesrisk.bean.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "service-patient", url = "${proxy.service.patient.url}")
public interface PatientServiceProxy {
    @GetMapping(value = "/patients/{id}")
    PatientBean getPatientById(@PathVariable("id") Integer id);
}
