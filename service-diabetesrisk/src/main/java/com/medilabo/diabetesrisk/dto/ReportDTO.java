package com.medilabo.diabetesrisk.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportDAO {
    private String riskFactor;

    private String description;

    private List<String> triggeredWords;
}
