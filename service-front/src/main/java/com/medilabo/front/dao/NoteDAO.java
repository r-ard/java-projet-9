package com.medilabo.front.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteDAO {
    private String id;

    @NotEmpty(message = "Content is mandatory")
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
