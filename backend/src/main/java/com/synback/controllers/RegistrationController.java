package com.synback.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import com.synback.utils.Data;
import com.synback.models.AuthenticationUser;
import com.synback.models.UserProfile;
import com.synback.repositories.AuthenticationRepository;
import com.synback.repositories.UserProfileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/profile")
@CrossOrigin(origins = { "*" }, allowedHeaders = { "*" })
public class RegistrationController {

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private UserProfileRepository userRepository;

    @PostMapping("/registration")
    public ResponseEntity<?> validateUserData(@RequestBody UserProfile data) {
        String name = data.getName();
        Data dateOfBirth = data.getDateOfBirth();
        int weight = data.getWeight();
        int height = data.getHeight();
        String gender = data.getGender();
        int exerciseTime = data.getExerciseTime();
        String diseaseHistory = data.getDiseaseHistory();
        String email = data.getEmail();

        AuthenticationUser credentials = authenticationRepository.findByEmail(email);
        String userId = credentials.getUserId();
        System.out.println("userId: " + userId);

        // Enviar dados para o servidor de socket
        try {
            String response = sendUserProfileToSocketServer(dateOfBirth, weight, height, exerciseTime);

            // Ler a resposta do servidor de socket
            System.out.println("Resposta: " + response);

            // Converter Data para Map<String, Integer>
            Map<String, Integer> dateOfBirthMap = new HashMap<>();
            dateOfBirthMap.put("dia", (int) dateOfBirth.getDia());
            dateOfBirthMap.put("mes", (int) dateOfBirth.getMes());
            dateOfBirthMap.put("ano", (int) dateOfBirth.getAno());

            // Se a resposta for "valido", salva no banco de dados
            if ("sucesso".equals(response)) {

                UserProfile userProfile = new UserProfile(userId, name, dateOfBirthMap, weight, height,
                        gender,
                        exerciseTime, diseaseHistory);
                userRepository.insert(userProfile);

                // System.out.println(userProfile);
                System.out.println("Informações cadastradas no banco de dados.");

                return ResponseEntity.ok("Usuário validado e salvo com sucesso.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public String sendUserProfileToSocketServer(Data dateOfBirth, int weight, int height, int exerciseTime) {
        try (Socket socket = new Socket("localhost", 7000);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("USER:" + dateOfBirth + ":" + weight + ":" + height + ":" + exerciseTime);
            String response = in.readLine();
            if (response.startsWith("erro:")) {
                throw new RuntimeException(response.substring(5)); // Remove "erro:"
            }
            return response;

        } catch (IOException e) {
            return "Erro ao se comunicar com o servidor de socket";
        }
    }
}
