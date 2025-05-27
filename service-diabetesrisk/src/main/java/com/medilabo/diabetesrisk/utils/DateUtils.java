package com.medilabo.diabetesrisk.utils;

import java.time.LocalDate;
import java.time.Period;

public abstract class DateUtils {
    public static Integer getAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
