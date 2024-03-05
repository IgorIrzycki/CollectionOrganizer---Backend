package com.example.collectionorganizer.repository;

import com.example.collectionorganizer.model.ItemSet;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemSetRepository extends MongoRepository<ItemSet, ObjectId> {
    ItemSet findByName(String name);

    @Query(value = "{ 'categoryName' : ?0 }", fields = "{ 'name' : 1, 'description' : 1, 'imageUrl' : 1, 'size' : 1 }")
    List<ItemSet> findByCategoryName(String categoryName, Pageable pageable);
}
