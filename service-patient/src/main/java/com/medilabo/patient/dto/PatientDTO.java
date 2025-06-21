package com.medilabo.patient.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDAO {
    private int id;

    @NotEmpty(message = "First name is mandatory")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;

    @NotEmpty(message = "Last name is mandatory")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String lastName;

    @NotNull(message = "Birth date is mandatory")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotEmpty(message = "Gender is mandatory")
    @Size(max = 1, message = "First name must not exceed one character")
    private String gender;

    @Size(max = 255, message = "First name must not exceed 255 characters")
    private String address;

    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String phoneNumber;
}
