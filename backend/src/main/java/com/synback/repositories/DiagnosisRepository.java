package com.synback.repositories;

import java.util.List;
import com.synback.models.UserDiagnosis;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DiagnosisRepository extends MongoRepository<UserDiagnosis, String> {
    List<UserDiagnosis> findByUserId(String id);
    UserDiagnosis findByDiagnosisId(String id);
}