package com.medilabo.front.proxy;

import com.medilabo.front.bean.ReportBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-diabetesrisk", url = "${proxy.service.diabetesrisk.url}")
public interface DiabetesRiskProxy {
    @GetMapping("/diabetes-risk/{patientId}")
    public ReportBean getPatientDiabetesRiskReport(@PathVariable("patientId")Integer patientId);
}
