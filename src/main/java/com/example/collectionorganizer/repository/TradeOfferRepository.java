package com.example.collectionorganizer.repository;

import com.example.collectionorganizer.model.TradeOffer;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeOfferRepository extends MongoRepository<TradeOffer, String> {
    List<TradeOffer> findByUsername1(String username1);
    List<TradeOffer> findByUsername2(String username2);
    void deleteById(String id);


    void deleteByItemSetNameAndUsername1(String itemSetName, String username1);
    void deleteByItemSetNameAndUsername2(String itemSetName, String username2);
}
