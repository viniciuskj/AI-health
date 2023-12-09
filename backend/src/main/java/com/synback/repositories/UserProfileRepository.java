package com.synback.repositories;

import com.synback.models.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserProfileRepository extends MongoRepository<UserProfile, String> {
    UserProfile findByUserId(String id);
}
