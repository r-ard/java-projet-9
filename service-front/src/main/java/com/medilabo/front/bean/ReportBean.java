package com.medilabo.front.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportBean {
    private String riskFactor;

    private String description;

    private List<String> triggeredWords;
}
