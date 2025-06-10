package com.medilabo.front.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteBean {
    private String id;

    private Integer patientId;

    private String content;

    private LocalDate date;
}