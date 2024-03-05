package com.example.collectionorganizer.service;

import com.example.collectionorganizer.model.Item;
import com.example.collectionorganizer.model.OwnedItem;
import com.example.collectionorganizer.repository.OwnedItemRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnedItemService {
    @Autowired
    private OwnedItemRepository ownedItemRepository;

    public List<OwnedItem> getOwnedItemsByUsername(String username){
        return ownedItemRepository.findByOwnedBy(username);
    }
    public List<OwnedItem> createOwnedItems(List<Item> items, String clickedItemName, String username) {
        List<OwnedItem> ownedItems = items.stream()
                .map(item -> {
                    int quantity = item.getName().equals(clickedItemName) ? 1 : 0;
                    return new OwnedItem(item.getName(), item.getImageUrl(), quantity, username);
                })
                .collect(Collectors.toList());

        return ownedItemRepository.saveAll(ownedItems);
    }

    public OwnedItem updateQuantity(String ownedBy, String name, int newQuantity) {
        OwnedItem ownedItem = ownedItemRepository.findByOwnedByAndName(ownedBy, name);

        if (ownedItem != null) {
            ownedItem.setQuantity(newQuantity);
            return ownedItemRepository.save(ownedItem);
        }
        return null;
    }


    public void deleteOwnedItems(List<OwnedItem> ownedItems) {
        // Pobieranie listy identyfikatorów przedmiotów do usunięcia
        List<ObjectId> itemIdsToDelete = ownedItems.stream()
                .map(OwnedItem::getId)
                .collect(Collectors.toList());

        // Usuwanie przedmiotów z repozytorium
        ownedItemRepository.deleteByIdIn(itemIdsToDelete);
    }


}
