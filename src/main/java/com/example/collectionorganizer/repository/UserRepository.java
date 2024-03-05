package com.example.collectionorganizer.repository;

import com.example.collectionorganizer.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}
