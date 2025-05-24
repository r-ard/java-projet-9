package com.medilabo.note.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "notes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    @Id
    private String id;

    private Integer patientId;

    private String content;

    private LocalDate date;
}
