package com.medilabo.diabetesrisk.service;

import com.medilabo.diabetesrisk.bean.NoteBean;
import com.medilabo.diabetesrisk.bean.PatientBean;
import com.medilabo.diabetesrisk.dao.ReportDAO;
import com.medilabo.diabetesrisk.domain.ReportRisk;
import com.medilabo.diabetesrisk.exception.PatientNotFoundException;
import com.medilabo.diabetesrisk.proxy.NoteServiceProxy;
import com.medilabo.diabetesrisk.proxy.PatientServiceProxy;
import com.medilabo.diabetesrisk.utils.DateUtils;
import com.medilabo.diabetesrisk.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Component
public class DiabetesRiskService {
    private final NoteServiceProxy noteServiceProxy;

    private final PatientServiceProxy patientServiceProxy;

    private final int ADULT_AGE = 30;

    @Value("${diabetes-risk.triggerwords}")
    private String diabetesRiskTriggereableWords;

    public DiabetesRiskService(NoteServiceProxy noteServiceProxy, PatientServiceProxy patientServiceProxy) {
        this.noteServiceProxy = noteServiceProxy;
        this.patientServiceProxy = patientServiceProxy;
    }

    public ReportDAO getPatientReport(Integer patientId) throws PatientNotFoundException {
        PatientBean patient = patientServiceProxy.getPatientById(patientId);
        if(patient == null) {
            throw new PatientNotFoundException(patientId);
        }

        List<NoteBean> patientNotes = noteServiceProxy.getPatientNotes(patientId);
        List<String> triggeredWords = this.getTriggeredWordsOfNotes(patientNotes);

        int patientAge = DateUtils.getAge(patient.getBirthDate());
        if(patientAge < ADULT_AGE) {
            return this.getReportAsYoung(patient.getGender(), triggeredWords);
        }

        return this.getReportAsAdult(patient.getGender(), triggeredWords);
    }

    protected ReportDAO getReportAsYoung(String gender, List<String> triggeredWords) {
        int triggeredAmount = triggeredWords.size();

        String reportDescription = DiabetesRiskService.generateReportDescription(gender, triggeredAmount);

        if((gender.equals("F") && triggeredAmount >= 7) || (gender.equals("M") && triggeredAmount >= 5)) {
            String targetAmount = "?";

            if(gender.equals("F")) {
                targetAmount = String.valueOf(7);
            }
            else if(gender.equals("M")) {
                targetAmount = String.valueOf(5);
            }

            return new ReportDAO(ReportRisk.EARLY_ONSET, reportDescription.replace("{}", targetAmount), triggeredWords);
        }
        else if((gender.equals("F") && triggeredAmount >= 4) || (gender.equals("M") && triggeredAmount >= 3)) {
            String targetAmount = "?";

            if(gender.equals("F")) {
                targetAmount = String.valueOf(4);
            }
            else if(gender.equals("M")) {
                targetAmount = String.valueOf(3);
            }

            return new ReportDAO(ReportRisk.IN_DANGER, reportDescription.replace("{}", targetAmount), triggeredWords);
        }

        return new ReportDAO(ReportRisk.NONE, reportDescription.replace("{}", String.valueOf(0)), triggeredWords);
    }

    protected ReportDAO getReportAsAdult(String gender, List<String> triggeredWords) {
        int triggeredAmount = triggeredWords.size();

        String reportDescription = DiabetesRiskService.generateReportDescription(gender, triggeredAmount);

        if(triggeredAmount >= 8) {
            return new ReportDAO(ReportRisk.EARLY_ONSET, reportDescription.replace("{}", String.valueOf(8)), triggeredWords);
        }
        else if(triggeredAmount >= 6) {
            return new ReportDAO(ReportRisk.IN_DANGER, reportDescription.replace("{}", String.valueOf(6)), triggeredWords);
        }
        else if(triggeredAmount >= 2) {
            return new ReportDAO(ReportRisk.BORDERLINE, reportDescription.replace("{}", String.valueOf(2)), triggeredWords);
        }

        return new ReportDAO(ReportRisk.NONE, reportDescription.replace("{}", String.valueOf(0)), triggeredWords);
    }

    protected List<String> getTriggeredWordsOfNotes(List<NoteBean> notes) {
        List<String> triggeredWords = new ArrayList<>();
        if(notes.isEmpty()) {
            return triggeredWords;
        }

        List<String> propertiesTriggereableWords = this.getPropertiesTriggereableWords();

        for(NoteBean note : notes) {
            // Clean HTML characters
            String cleanedContent = StringUtils.getHTMLStringContent(note.getContent());

            // Normalize to ascii
            cleanedContent = StringUtils.normalizeToAscii(cleanedContent);

            // Put it to lowercase
            cleanedContent = cleanedContent.toLowerCase();

            // For each word in the properties triggereable words, check if it is present in the cleaned content
            // If it is, add it to the triggered words list
            for(String triggereableWord : propertiesTriggereableWords) {
                if(triggeredWords.contains(triggereableWord) || !cleanedContent.contains(triggereableWord.toLowerCase())) {
                    continue;
                }

                triggeredWords.add(triggereableWord);
            }
        }

        return triggeredWords;
    }

    protected List<String> getPropertiesTriggereableWords() {
        String[] explodedProperties = this.diabetesRiskTriggereableWords.split(",");
        return Arrays.stream(explodedProperties).map(word -> word.trim()).toList();
    }

    private static String generateReportDescription(String gender, int triggeredAmount) {
        return "Le patient est " + (gender.equals("F") ? "une femme" : "un homme") + " agé(e) de plus de 30 ans et a {} termes déclancheurs ou plus, en l'occurence " + triggeredAmount + " termes.";
    }
}
