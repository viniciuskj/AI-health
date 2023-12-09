package com.synback.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.synback.utils.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Map;

@Document(collection = "User")
public class UserProfile {
    private String userId;
    private String name;
    private Data dateOfBirth;
    private int weight;
    private int height;
    private String gender;
    private int exerciseTime;
    private String diseaseHistory;
    private String subscriptionPlan;
    private String email;

    public UserProfile() {
    }

    @JsonCreator
    public UserProfile(@JsonProperty("userId") String userId,
            @JsonProperty("name") String name,
            @JsonProperty("dateOfBirth") Map<String, Integer> dateOfBirthMap,
            @JsonProperty("weight") int weight,
            @JsonProperty("height") int height,
            @JsonProperty("gender") String gender,
            @JsonProperty("exerciseTime") int exerciseTime,
            @JsonProperty("diseaseHistory") String diseaseHistory) {
        this.userId = userId;
        this.name = name;
        this.setBirthDateFromMap(dateOfBirthMap);
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.exerciseTime = exerciseTime;
        this.diseaseHistory = diseaseHistory;
        this.subscriptionPlan = "Básico";
    }

    public String getId() {
        return userId;
    }

    public void setId(String id) {
        this.userId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Data getDateOfBirth() {
        return dateOfBirth;
    }

    public void setBirthDate(byte day, byte month, short year) {
        try {
            Data date = new Data(day, month, year);
            this.dateOfBirth = date;
        } catch (Exception e) {
            System.err.println("Erro ao definir data de nascimento: " + e.getMessage());
        }
    }

    public void setBirthDateFromData(Data dateOfBirth) {
        if (dateOfBirth != null) {
            byte day = (byte) dateOfBirth.getDia();
            byte month = (byte) dateOfBirth.getMes();
            short year = (short) dateOfBirth.getAno();

            this.setBirthDate(day, month, year);
        } else {
            System.err.println("Objeto Data é nulo.");
        }
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getExerciseTime() {
        return exerciseTime;
    }

    public void setExerciseTime(int exerciseTime) {
        this.exerciseTime = exerciseTime;
    }

    public String getDiseaseHistory() {
        return diseaseHistory;
    }

    public void setDiseaseHistory(String diseaseHistory) {
        this.diseaseHistory = diseaseHistory;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Método ajustado para aceitar um Map e configurar a data
    public void setBirthDateFromMap(Map<String, Integer> dateOfBirthMap) {
        if (dateOfBirthMap != null) {
            byte day = dateOfBirthMap.get("dia").byteValue();
            byte month = dateOfBirthMap.get("mes").byteValue();
            short year = dateOfBirthMap.get("ano").shortValue();
            this.setBirthDate(day, month, year);
        } else {
            System.err.println("Mapa da data de nascimento é nulo.");
        }
    }

    @Override
    public String toString() {
        return "Id: " + userId + '\n' +
                "Name: " + name + '\n' +
                "DateOfBirth: " + dateOfBirth.toString() + '\n' +
                "Weight: " + weight + '\n' +
                "Height: " + height + '\n' +
                "Gender: " + gender + '\n' +
                "DailyExerciseTime: " + exerciseTime + '\n' +
                "DiseasesInTheFamily: " + diseaseHistory + '\n' +
                "SubscriptionPlan: " + subscriptionPlan + '\n' +
                "Email: " + email + '\n';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null)
            return false;
        if (obj.getClass() != this.getClass())
            return false;

        UserProfile user = (UserProfile) obj;

        if (user.userId != this.userId ||
                user.name != this.name ||
                user.dateOfBirth != this.dateOfBirth ||
                user.weight != this.weight ||
                user.height != this.height ||
                user.gender != this.gender ||
                user.exerciseTime != this.exerciseTime ||
                user.diseaseHistory != this.diseaseHistory ||
                user.email != this.email)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 13;

        result = 7 * result + userId.hashCode();
        result = 7 * result + name.hashCode();
        result = 7 * result + dateOfBirth.hashCode();
        result = 7 * result + Double.hashCode(weight);
        result = 7 * result + Integer.hashCode(height);
        result = 7 * result + gender.hashCode();
        result = 7 * result + Integer.hashCode(exerciseTime);
        result = 7 * result + diseaseHistory.hashCode();
        result = 7 * result + email.hashCode();

        if (result < 0)
            result = -result;

        return result;
    }

    private UserProfile(UserProfile modelo) throws Exception {
        if (modelo == null)
            throw new Exception("modelo ausente");

        this.userId = modelo.userId;
        this.name = modelo.name;
        this.dateOfBirth = modelo.dateOfBirth;
        this.weight = modelo.weight;
        this.height = modelo.height;
        this.gender = modelo.gender;
        this.exerciseTime = modelo.exerciseTime;
        this.diseaseHistory = modelo.diseaseHistory;
        this.email = modelo.email;
    }

    public Object clone() {
        UserProfile ret = null;

        try {
            ret = new UserProfile(this);
        } catch (Exception erro) {
        }

        return ret;
    }
}
