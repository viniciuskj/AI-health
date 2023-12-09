package com.synback.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.synback.models.AuthenticationUser;
import com.synback.models.UserDiagnosis;
import com.synback.models.UserDiagnosis.ReportItem;
import com.synback.models.UserProfile;
import com.synback.models.UserProfileDTO;
import com.synback.repositories.AuthenticationRepository;
import com.synback.repositories.DiagnosisRepository;
import com.synback.repositories.UserProfileRepository;
import com.synback.services.DiagnosticsService;
import java.util.Random;

@RestController
@RequestMapping("/profile")
@CrossOrigin(origins = { "*" }, allowedHeaders = { "*" }) // Permitir CORS para o frontend
public class DiagnosticController {

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private DiagnosticsService diagnosticsService;

    @Autowired
    private UserProfileRepository userRepository;

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @PostMapping("/diagnosis")
    public ResponseEntity<?> newDiagnosis(@RequestBody UserDiagnosis data) {
        String symptoms = data.getSymptoms();

        // Obtem o id do usuário
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();

        AuthenticationUser credentials = authenticationRepository.findByEmail(email);
        String userId = credentials.getUserId();

        UserProfile user = userRepository.findByUserId(userId);

        if (user == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        // Envia sintomas e dados do perfil do usuário para o OpenAI
        String prompt = diagnosticsService.buildPrompt(user, symptoms);
        System.out.println(prompt);

        String openAiResponse = diagnosticsService.callOpenAiService(prompt);
        System.out.println("OpenAI response: " + "\n" + openAiResponse);

        List<ReportItem> report = parseOpenAiResponse(openAiResponse);
        System.out.println("Report: " + "\n" + report);

        // Gera um ID único
        String uniqueId = generateUniqueId();

        // Salva o diagnóstico no banco de dados
        UserDiagnosis diagnosis = new UserDiagnosis(uniqueId, report, symptoms, user.getId(), new Date());
        System.out.println("Diagnóstico: " + "\n" + diagnosis);
        diagnosisRepository.insert(diagnosis);

        // Retorna o ID na resposta
        return ResponseEntity.ok(Map.of("id", uniqueId));
    }

    private static String generateUniqueId() {
        Random random = new Random();
        // Gera um número aleatório com 10 dígitos
        int number = random.nextInt(1000000000, 2000000000);
        return "SYN-" + String.format("%010d", number);
    }

    // Deixa a resposta da OpenAI no formato da classe UserDiagnostics - ReportItem
    private List<ReportItem> parseOpenAiResponse(String response) {
        List<UserDiagnosis.ReportItem> reportItems = new ArrayList<>();
        Pattern pattern = Pattern.compile(
                "Problema: (.*?)\\nChance de ocorrer: (.*?)\\nDescrição: (.*?)(?=\\n\\nProblema:|\\Z)", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(response);

        while (matcher.find()) {
            String problem = matcher.group(1).trim();
            String chanceOfOccurrence = matcher.group(2).trim();
            String description = matcher.group(3).trim();
            reportItems.add(new UserDiagnosis.ReportItem(problem, chanceOfOccurrence, description));
        }
        return reportItems;
    }

    @GetMapping("/diagnosis")
    public ResponseEntity<?> getUserData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();

        AuthenticationUser credentials = authenticationRepository.findByEmail(email);
        String userId = credentials.getUserId();

        UserProfile userInfo = userRepository.findByUserId(userId);

        UserProfileDTO userProfileDTO = null;
        if (userInfo != null) {
            userProfileDTO = new UserProfileDTO();
            userProfileDTO.setUserId(userInfo.getId());
            userProfileDTO.setName(userInfo.getName());
        }

        return ResponseEntity.ok(userProfileDTO);
    }
}