package com.synback.services;

import com.synback.models.UserProfile;
import java.net.http.HttpRequest;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.Calendar;
import com.synback.utils.Data;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DiagnosticsService {

    @Value("${openai.token}")
    private String openaiApiKey;

    private int calculateAge(Data birthData) {
        Calendar birthDate = Calendar.getInstance();
        birthDate.set(Calendar.YEAR, birthData.getAno());
        birthDate.set(Calendar.MONTH, birthData.getMes() - 1); // O mês no Calendar começa do 0
        birthDate.set(Calendar.DAY_OF_MONTH, birthData.getDia());

        Calendar currentDate = Calendar.getInstance();

        int age = currentDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
        if (birthDate.get(Calendar.DAY_OF_YEAR) > currentDate.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age;
    }

    public String buildPrompt(UserProfile user, String symptoms) {
        int age = calculateAge(user.getDateOfBirth());

        return "Por favor, analise as seguintes informações e forneça uma lista de três possíveis problemas de saúde. Para cada problema, indique o nome do problema, a chance de ocorrer (baixa, moderada ou alta) e uma breve descrição. Formate sua resposta como: 'Problema: [nome do problema], Chance de ocorrer: [baixa/moderada/alta], Descrição: [descrição]'.\n\n"
                +
                "Informações do usuário:\n" +
                "- Idade: " + age + "\n" +
                "- Gênero: " + user.getGender() + "\n" +
                "- Peso: " + user.getWeight() + "\n" +
                "- Altura: " + user.getHeight() + "\n" +
                "- Doenças na família: " + user.getDiseaseHistory() + "\n" +
                "- Tempo médio de exercícios por dia: " + user.getExerciseTime() + "\n" +
                "- Sintoma: " + symptoms + "\n";
    }

    // Extrai apenas o conteúdo do relatório do OpenAI
    private String extractContentFromResponse(String response) {
        String startToken = "\"content\": \"";
        int startIndex = response.indexOf(startToken);
    
        if (startIndex == -1) {
            return ""; // Retorna string vazia se não encontrar o token
        }
    
        // Pega somente o conteúdo somente após o token
        startIndex += startToken.length();
    
        // Encontrar o índice de fim baseado em um padrão específico
        Pattern endPattern = Pattern.compile("Descrição: [^\\n]*\\n"); // Procura por "Descrição:" seguido de algo que não seja uma nova linha
        Matcher matcher = endPattern.matcher(response.substring(startIndex));
        int endIndex = startIndex;
        while (matcher.find()) {
            endIndex = startIndex + matcher.end(); // Atualizar endIndex para o final da última descrição
        }

        String content = response.substring(startIndex, endIndex).replace("\\n", "\n").trim();
        content = content.replaceAll("\"$", ""); // Remove aspas no final da string
        return content;
    }
    

    // Enviar para ChatGPT
    public String callOpenAiService(String prompt) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Authorization", "Bearer " + openaiApiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(createRequestBody(prompt)))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String content = extractContentFromResponse(response.body());
            return content;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String createRequestBody(String prompt) {
        JsonObject messageUser = new JsonObject();
        messageUser.addProperty("role", "user");
        messageUser.addProperty("content", prompt);

        JsonObject messageSystem = new JsonObject();
        messageSystem.addProperty("role", "system");
        messageSystem.addProperty("content", "Seu prompt aqui");

        JsonArray messages = new JsonArray();
        messages.add(messageSystem);
        messages.add(messageUser);

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", "gpt-3.5-turbo");
        requestBody.add("messages", messages);

        return requestBody.toString();
    }
}
