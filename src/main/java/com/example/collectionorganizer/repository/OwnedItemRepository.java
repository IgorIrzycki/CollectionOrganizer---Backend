package com.example.collectionorganizer.repository;

import com.example.collectionorganizer.model.OwnedItem;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface OwnedItemRepository extends MongoRepository<OwnedItem, ObjectId> {
    OwnedItem findByOwnedByAndName(String ownedBy, String name);
    List<OwnedItem> findByOwnedBy(String ownedBy);

    void deleteByIdIn(Collection<ObjectId> id);
}
