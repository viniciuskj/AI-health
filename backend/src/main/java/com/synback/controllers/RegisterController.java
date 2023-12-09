package com.synback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import com.synback.models.AuthenticationUser;
import com.synback.repositories.AuthenticationRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "*" }, allowedHeaders = { "*" })
public class RegisterController {

    @Autowired
    private AuthenticationRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> validateCredentials(@RequestBody AuthenticationUser credentials) {

        String email = credentials.getEmail();
        String password = credentials.getPassword();

        System.out.println("Email: " + email);
        System.out.println("Senha: " + password);

        // Enviar dados para o servidor de socket
        try {
            String response = sendCredentialsToSocketServer(email, password);

            // Ler a resposta do servidor de socket
            System.out.println("Resposta: " + response);

            // Se a resposta for "valido", salva no banco de dados
            if ("valido".equals(response)) {
                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                AuthenticationUser user = new AuthenticationUser(generateUniqueId(), email, hashedPassword);
                userRepository.insert(user);

                // System.out.println(user);
                System.out.println("Usuário salvo no banco de dados.");

                return ResponseEntity.ok("Credenciais validadas e salvas com sucesso.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public String sendCredentialsToSocketServer(String email, String password) {
        try (Socket socket = new Socket("localhost", 7000);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("CRED:" + email + ":" + password);
            String response = in.readLine();
            if (response.startsWith("erro:")) {
                throw new RuntimeException(response.substring(5)); // Remove "erro:"
            }
            return response;

        } catch (IOException e) {
            return "Erro ao se comunicar com o servidor de socket";
        }
    }

    private static String generateUniqueId() {
        Random random = new Random();
        // Gera um número aleatório com 10 dígitos
        int number = random.nextInt(1000000000, 2000000000);
        return "SYN-" + String.format("%010d", number);
    }
}