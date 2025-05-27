package com.medilabo.diabetesrisk.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteBean {
    private String id;

    private Integer patientId;

    private String content;
}
