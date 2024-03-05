package com.example.collectionorganizer.repository;

import com.example.collectionorganizer.model.Category;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends MongoRepository<Category, ObjectId> {
    Category findByName(String name);

    @Query(value = "{}", fields = "{ 'name' : 1, 'description' : 1, 'imageUrl' : 1, 'size' :  1}")
    List<Category> findAllProjectedBy();
}
