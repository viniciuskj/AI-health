package com.synback.controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.synback.models.AuthenticationUser;
import com.synback.models.UserDiagnosis;
import com.synback.models.UserProfile;
import com.synback.models.UserProfileDTO;
import com.synback.repositories.AuthenticationRepository;
import com.synback.repositories.DiagnosisRepository;
import com.synback.repositories.UserProfileRepository;

@RestController
@RequestMapping("/profile")
@CrossOrigin(origins = { "*" }, allowedHeaders = { "*" }) // Permitir CORS para o frontend
public class DiagnosticIdController {

    @Autowired
    private UserProfileRepository userRepository;

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @GetMapping("/diagnosis/{diagnosisId}")
    public ResponseEntity<?> getDiagnosisById(@PathVariable String diagnosisId) {

        // Obter diagnostico correspondente ao id
        UserDiagnosis diagnoses = diagnosisRepository.findByDiagnosisId(diagnosisId);
        System.out.println("diagnoses: " + diagnoses);

        // Obter dados do usuário
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

        Map<String, Object> response = new HashMap<>();
        response.put("diagnoses", diagnoses);
        response.put("userInfo", userProfileDTO);

        if (!response.isEmpty()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
