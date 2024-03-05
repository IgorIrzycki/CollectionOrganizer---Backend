package com.example.collectionorganizer.service;

import com.example.collectionorganizer.model.*;
import com.example.collectionorganizer.repository.TradeOfferRepository;
import com.example.collectionorganizer.repository.UserRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TradeOfferService {
    @Autowired
    private TradeOfferRepository tradeOfferRepository;
    @Autowired
    private OwnedItemService ownedItemService;
    @Autowired
    private MongoTemplate mongoTemplate;

    public void createTradeOffer(TradeOffer tradeOffer) {
        tradeOfferRepository.save(tradeOffer);
    }

    public List<TradeOffer> getUserTradeOffers(String username) {
        return tradeOfferRepository.findByUsername1(username);
    }

    public List<TradeOffer> getOtherUserTradeOffers(String username) {
        return tradeOfferRepository.findByUsername2(username);
    }

    public void deleteTradeOffersForItemSet(String username, String itemSetName) {
        tradeOfferRepository.deleteByItemSetNameAndUsername1(itemSetName, username);
        tradeOfferRepository.deleteByItemSetNameAndUsername2(itemSetName, username);
    }

    public void deleteTradeOfferById(String id) {
        tradeOfferRepository.deleteById(id);
    }

    public void rejectTradeOffer(String offerId) throws ChangeSetPersister.NotFoundException {
        TradeOffer tradeOffer = tradeOfferRepository.findById(offerId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        tradeOffer.setDenied(true);

        tradeOfferRepository.save(tradeOffer);
    }

    public List<String> confirmTradeOffer(String offerId) throws ChangeSetPersister.NotFoundException {
        TradeOffer tradeOffer = tradeOfferRepository.findById(offerId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        List<String> missingItemsUser1 = checkItemAvailability(tradeOffer.getUsername1(), true, tradeOffer.getSelectedItemsToTrade());
        List<String> missingItemsUser2 = checkItemAvailability(tradeOffer.getUsername2(), false, tradeOffer.getSelectedWantedItems());
        if (!missingItemsUser1.isEmpty() || !missingItemsUser2.isEmpty()) {
            return Stream.concat(missingItemsUser1.stream(), missingItemsUser2.stream())
                    .collect(Collectors.toList());
        }
        List<OwnedItem> username1OwnedItems = ownedItemService.getOwnedItemsByUsername(tradeOffer.getUsername1());
        List<OwnedItem> username2OwnedItems = ownedItemService.getOwnedItemsByUsername(tradeOffer.getUsername2());
        if (username1OwnedItems.isEmpty() && username2OwnedItems.isEmpty()) {
            throw new ChangeSetPersister.NotFoundException();
        }
        updateItemsQuantities(tradeOffer.getUsername1(), tradeOffer.getUsername2(), tradeOffer.getSelectedItemsToTrade(),
                tradeOffer.getSelectedWantedItems(), username1OwnedItems, username2OwnedItems);
        tradeOffer.setAccepted(true);
        tradeOfferRepository.save(tradeOffer);
        return Collections.emptyList();
    }
    private void updateItemsQuantities(String username1, String username2, List<TradeItem> itemsToTrade, List<TradeItem> wantedItems,
                                       List<OwnedItem> ownedItemsUser1, List<OwnedItem> ownedItemsUser2) {
        for (TradeItem item : itemsToTrade) {
            OwnedItem ownedItemUser1 = findItemInOwnedItems(ownedItemsUser1, item.getItemName());
            assert ownedItemUser1 != null;
            OwnedItem ownedItemUser2 = findItemInOwnedItems(ownedItemsUser2, item.getItemName());
            assert ownedItemUser2 != null;
            ownedItemService.updateQuantity(username1, item.getItemName(), ownedItemUser1.getQuantity() - item.getQuantityUser());
            ownedItemService.updateQuantity(username2, item.getItemName(), ownedItemUser2.getQuantity() + item.getQuantityUser());
        }

        for (TradeItem item : wantedItems) {
            OwnedItem ownedItemUser1 = findItemInOwnedItems(ownedItemsUser1, item.getItemName());
            assert ownedItemUser1 != null;
            OwnedItem ownedItemUser2 = findItemInOwnedItems(ownedItemsUser2, item.getItemName());
            assert ownedItemUser2 != null;
            ownedItemService.updateQuantity(username1, item.getItemName(), ownedItemUser1.getQuantity() + item.getQuantityOtherUser());
            ownedItemService.updateQuantity(username2, item.getItemName(), ownedItemUser2.getQuantity() - item.getQuantityOtherUser());
        }
    }
    private List<String> checkItemAvailability(String username, Boolean username1, List<TradeItem> tradeItems) {
        List<String> missingItems = new ArrayList<>();
        User user = mongoTemplate.findOne(
                Query.query(Criteria.where("username").is(username)),
                User.class
        );

        for (TradeItem tradeItem : tradeItems) {
            assert user != null;
            OwnedItem ownedItem = findItemInCollection(user.getOwnedItemSets(), tradeItem.getItemName());
            int tradeItemQuantity = (username1 ? tradeItem.getQuantityUser() : tradeItem.getQuantityOtherUser());
            if (ownedItem == null || ownedItem.getQuantity() < tradeItemQuantity) {
                missingItems.add(tradeItem.getItemName());
            }
        }
        return missingItems;
    }

    private OwnedItem findItemInCollection(List<OwnedItemSet> ownedItemSets, String itemName) {
        for (OwnedItemSet itemSet : ownedItemSets) {
            for (OwnedItem item : itemSet.getOwnedItems()) {
                if (item.getName().equals(itemName)) {
                    return item;
                }
            }
        }
        return null;
    }

    private OwnedItem findItemInOwnedItems(List<OwnedItem> ownedItems, String itemName) {

        for (OwnedItem item : ownedItems) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }

        return null;
    }
}
