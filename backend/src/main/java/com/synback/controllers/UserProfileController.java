package com.synback.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.synback.models.AuthenticationUser;
import com.synback.models.UserDiagnosis;
import com.synback.models.UserDiagnosisDTO;
import com.synback.models.UserProfile;
import com.synback.models.UserProfileDTO;
import com.synback.repositories.AuthenticationRepository;
import com.synback.repositories.DiagnosisRepository;
import com.synback.repositories.UserProfileRepository;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "*" }, allowedHeaders = { "*" })
public class UserProfileController {

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private UserProfileRepository userRepository;

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @GetMapping("/dashboard")
    public ResponseEntity<?> getUserData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        
        AuthenticationUser credentials = authenticationRepository.findByEmail(email);
        String userId = credentials.getUserId();
        System.out.println("userId: " + userId);

        List<UserDiagnosis> diagnoses = diagnosisRepository.findByUserId(userId);
        
        List<UserDiagnosisDTO> diagnosisDTOs = diagnoses.stream().map(diagnosis -> {
            UserDiagnosisDTO diagnosisDTO = new UserDiagnosisDTO();
            diagnosisDTO.setDiagnosisId(diagnosis.getDiagnosisId());
            diagnosisDTO.setSymptoms(diagnosis.getSymptoms());
            diagnosisDTO.setCurrentDate(diagnosis.getCurrentDate());
            System.out.println("diagnosisDTOs: " + diagnosisDTO);
            return diagnosisDTO;
        }).collect(Collectors.toList());
        
        
        UserProfile userInfo = userRepository.findByUserId(userId);
        System.out.println("userInfo: " + userInfo);
        
        UserProfileDTO userProfileDTO = null;
        if (userInfo != null) {
            userProfileDTO = new UserProfileDTO();
            userProfileDTO.setUserId(userInfo.getId());
            userProfileDTO.setName(userInfo.getName());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("diagnoses", diagnosisDTOs);
        response.put("userInfo", userProfileDTO);

        if (!response.isEmpty()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
