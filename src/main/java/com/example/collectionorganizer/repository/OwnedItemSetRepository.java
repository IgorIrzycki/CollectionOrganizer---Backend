package com.example.collectionorganizer.repository;

import com.example.collectionorganizer.model.Category;
import com.example.collectionorganizer.model.OwnedItemSet;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnedItemSetRepository extends MongoRepository<OwnedItemSet, ObjectId> {
    OwnedItemSet findByOwnedByAndName(String ownedBy, String name);

    @Query(value = "{}", fields = "{ 'name' : 1, 'description' : 1, 'imageUrl' : 1, 'size' :  1}")
    List<OwnedItemSet> findAllProjectedBy();
}
