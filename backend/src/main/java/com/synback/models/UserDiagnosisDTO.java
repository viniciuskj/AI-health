package com.synback.models;

import java.util.Date;

public class UserDiagnosisDTO implements Cloneable {
    private String diagnosisId;
    private String symptoms;
    private Date currentDate;

    public UserDiagnosisDTO() {
    }

    public UserDiagnosisDTO(String diagnosisId, String symptoms, Date currentDate) {
        this.diagnosisId = diagnosisId;
        this.symptoms = symptoms;
        this.currentDate = currentDate;
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    @Override
    public String toString() {
        return "Symptoms: " + symptoms + '\n' +
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

        UserDiagnosisDTO diagnosis = (UserDiagnosisDTO) obj;

        if (diagnosis.diagnosisId != this.diagnosisId || diagnosis.symptoms != this.symptoms ||
                diagnosis.currentDate != this.currentDate)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 13;

        result = 7 * result + diagnosisId.hashCode();
        result = 7 * result + symptoms.hashCode();
        result = 7 * result + currentDate.hashCode();

        if (result < 0)
            result = -result;

        return result;
    }

    private UserDiagnosisDTO(UserDiagnosisDTO modelo) throws Exception {
        if (modelo == null)
            throw new Exception("modelo ausente");

        this.diagnosisId = modelo.diagnosisId;
        this.symptoms = modelo.symptoms;
        this.currentDate = modelo.currentDate;
    }

    public Object clone() {
        UserDiagnosisDTO ret = null;

        try {
            ret = new UserDiagnosisDTO(this);
        } catch (Exception erro) {
        }

        return ret;
    }

}
