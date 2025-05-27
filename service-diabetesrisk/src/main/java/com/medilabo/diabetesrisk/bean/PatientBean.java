package com.medilabo.diabetesrisk.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientBean {
    private Integer id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String gender;

    private String address;

    private String phoneNumber;
}
