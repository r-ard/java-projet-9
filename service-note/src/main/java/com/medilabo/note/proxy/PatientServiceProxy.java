package com.medilabo.note.proxy;

import com.medilabo.note.bean.PatientBean;
import com.medilabo.note.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-patient", url = "${proxy.service.patient.url}", configuration = FeignClientConfig.class)
public interface PatientServiceProxy {
    @GetMapping(value = "/patients/{id}")
    PatientBean getPatientById(@PathVariable("id") Integer id);
}
