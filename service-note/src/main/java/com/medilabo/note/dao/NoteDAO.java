package com.medilabo.note.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteDAO {
    private String id;

    @NotNull
    private Integer patientId;

    @NotEmpty(message = "Content is mandatory")
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
