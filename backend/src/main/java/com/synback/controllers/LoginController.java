package com.synback.controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.synback.models.AuthenticationUser;
import com.synback.services.AuthenticationService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "*" }, allowedHeaders = { "*" })
public class LoginController {

    @Autowired
    private AuthenticationService authService;

    @PostMapping("/login")
    public ResponseEntity<?> validateCredentialsInMongo(@RequestBody AuthenticationUser credentials) {
        try {
            String email = credentials.getEmail();
            String password = credentials.getPassword();

            // System.out.println("E-mail: " + email);
            // System.out.println("Senha: " + password);

            // O serviço de autenticação verifica as credenciais e retorna um token
            String token = authService.login(email, password);
            
            // Cria uma resposta com o token
            Map<String, String> tokenResponse = new HashMap<>();
            tokenResponse.put("token", token);
            // System.out.println("Token: " + token);

            // Retorna o token para o cliente
            System.out.println("Credenciais validadas.");
            return ResponseEntity.ok(tokenResponse);
            // return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            // Se ocorrer um erro de autenticação, retorna um erro HTTP 401 - Não autorizado
            return ResponseEntity.status(401).body(e.getMessage());
        }

    }
}
