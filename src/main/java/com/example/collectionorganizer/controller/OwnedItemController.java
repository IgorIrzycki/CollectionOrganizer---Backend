package com.example.collectionorganizer.controller;

import com.example.collectionorganizer.model.OwnedItem;
import com.example.collectionorganizer.service.OwnedItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/owned-item")
@RequiredArgsConstructor
public class OwnedItemController {
    @Autowired
    OwnedItemService ownedItemService;

    @PutMapping("/{username}/{itemName}/update-quantity/{newQuantity}")
    public ResponseEntity<OwnedItem> updateQuantity(@PathVariable(value = "username") String username, @PathVariable(value = "itemName") String itemName, @PathVariable int newQuantity) {
        System.out.println("Item name: "+ itemName);
        OwnedItem updatedItem = ownedItemService.updateQuantity(username, itemName, newQuantity);
        if (updatedItem != null) {
            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
