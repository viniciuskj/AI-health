package com.synback.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "Diagnosis")
public class UserDiagnosis implements Cloneable {

    @Id
    private String id;
    private String diagnosisId;
    private List<ReportItem> report;
    private String symptoms;
    private String userId;
    private Date currentDate;

    public UserDiagnosis() {
    }

    public UserDiagnosis(String diagnosisId, List<ReportItem> report, String symptoms, String userId,
            Date currentDate) {
        this.diagnosisId = diagnosisId;
        this.report = report;
        this.symptoms = symptoms;
        this.userId = userId;
        this.currentDate = currentDate;
    }

    public String getId() {
        return id;
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public List<ReportItem> getReport() {
        return report;
    }

    public void setReport(List<ReportItem> report) {
        this.report = report;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public static class ReportItem {
        private String problem;
        private String chanceOfOccurrence;
        private String description;

        public ReportItem(String problem, String chanceOfOccurrence, String description) {
            this.problem = problem;
            this.chanceOfOccurrence = chanceOfOccurrence;
            this.description = description;
        }

        public String getProblem() {
            return problem;
        }

        public void setProblem(String problem) {
            this.problem = problem;
        }

        public String getChanceOfOccurrence() {
            return chanceOfOccurrence;
        }

        public void setChanceOfOccurrence(String chanceOfOccurrence) {
            this.chanceOfOccurrence = chanceOfOccurrence;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return "Problem: " + problem + '\n' +
                    "ChanceOfOccurrence: " + chanceOfOccurrence + '\n' +
                    "Description: " + description;
        }
    }

    @Override
    public String toString() {
        return "Id: " + id + '\n' +
                "DiagnosisId: " + diagnosisId + '\n' +
                "Report: " + report + '\n' + '\n' +
                "Symptoms: " + symptoms + '\n' +
                "UserId: " + userId + '\n' +
                "CurrentDate: " + currentDate + '\n';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null)
            return false;
        if (obj.getClass() != this.getClass())
            return false;

        UserDiagnosis diagnosis = (UserDiagnosis) obj;

        if (diagnosis.id != this.id ||
                diagnosis.diagnosisId != this.diagnosisId ||
                diagnosis.report != this.report ||
                diagnosis.symptoms != this.symptoms ||
                diagnosis.userId != this.userId ||
                diagnosis.currentDate != this.currentDate)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 13;

        result = 7 * result + id.hashCode();
        result = 7 * result + diagnosisId.hashCode();
        result = 7 * result + report.hashCode();
        result = 7 * result + symptoms.hashCode();
        result = 7 * result + userId.hashCode();
        result = 7 * result + currentDate.hashCode();

        if (result < 0)
            result = -result;

        return result;
    }

    private UserDiagnosis(UserDiagnosis modelo) throws Exception {
        if (modelo == null)
            throw new Exception("modelo ausente");

        this.diagnosisId = modelo.diagnosisId;
        this.report = modelo.report != null ? new ArrayList<>(modelo.report) : null;
        this.symptoms = modelo.symptoms;
        this.userId = modelo.userId;
        this.currentDate = modelo.currentDate;
    }

    public Object clone() {
        UserDiagnosis ret = null;

        try {
            ret = new UserDiagnosis(this);
        } catch (Exception erro) {
        }

        return ret;
    }
}
