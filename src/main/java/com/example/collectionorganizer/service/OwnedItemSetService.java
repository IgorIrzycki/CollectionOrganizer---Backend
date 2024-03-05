package com.example.collectionorganizer.service;

import com.example.collectionorganizer.model.*;
import com.example.collectionorganizer.repository.ItemSetRepository;
import com.example.collectionorganizer.repository.OwnedItemSetRepository;
import com.example.collectionorganizer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OwnedItemSetService {
    @Autowired
    private OwnedItemSetRepository ownedItemSetRepository;
    @Autowired
    private OwnedItemService ownedItemService;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ItemSetRepository itemSetRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TradeOfferService tradeOfferService;
    public void deleteOwnedItemSet(String ownedBy, String name) {
        OwnedItemSet existingOwnedItemSet = ownedItemSetRepository.findByOwnedByAndName(ownedBy, name);
        if (existingOwnedItemSet == null) {
            throw new IllegalArgumentException("Item set not found");
        }

        tradeOfferService.deleteTradeOffersForItemSet(ownedBy, name);

        ownedItemService.deleteOwnedItems(existingOwnedItemSet.getOwnedItems());

        ownedItemSetRepository.delete(existingOwnedItemSet);

        User user = mongoTemplate.findOne(
                Query.query(Criteria.where("username").is(ownedBy)),
                User.class
        );

        if (user != null) {
            user.getOwnedItemSets().remove(existingOwnedItemSet);
            userRepository.save(user);
        }
    }

    public OwnedItemSet getOwnedItemSetByName(String ownedBy, String name) {
        return ownedItemSetRepository.findByOwnedByAndName(ownedBy, name);
    }

    public OwnedItemSet createOwnedItemSet(String name, String description, String imageUrl, String clickedItemName, String username, String categoryName) {
        User user = mongoTemplate.findOne(
                Query.query(Criteria.where("username").is(username)),
                User.class
        );

        OwnedItemSet existingOwnedItemSet = ownedItemSetRepository.findByOwnedByAndName(username, name);
        if (existingOwnedItemSet != null) {
            throw new IllegalArgumentException("You already have this set in your collection.");
        }

        ItemSet foundItemSet = itemSetRepository.findByName(name);
        List<Item> items = foundItemSet.getItems();

        List<OwnedItem> ownedItems = ownedItemService.createOwnedItems(items, clickedItemName, username);

        OwnedItemSet ownedItemSet = OwnedItemSet.builder()
                .name(name)
                .description(description)
                .imageUrl(imageUrl)
                .ownedItems(ownedItems)
                .categoryName(categoryName)
                .ownedBy(username)
                .build();

        OwnedItemSet savedOwnedItemSet = ownedItemSetRepository.insert(ownedItemSet);

        if (user != null) {
            System.out.println("User found: " + user);
            List<OwnedItemSet> ownedItemSets = user.getOwnedItemSets();
            if (ownedItemSets == null) {
                ownedItemSets = new ArrayList<>();
            }
            ownedItemSets.add(savedOwnedItemSet);
            user.setOwnedItemSets(ownedItemSets);
            userRepository.save(user);
        }

        return savedOwnedItemSet;
    }

    public List<OwnedItemSet> allOwnedItemSets() {
        return ownedItemSetRepository.findAll();
    }
}
