package com.example.collectionorganizer.controller;

import com.example.collectionorganizer.model.Category;
import com.example.collectionorganizer.model.OwnedItemSet;
import com.example.collectionorganizer.service.OwnedItemSetService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/owned-item-set")
@RequiredArgsConstructor
public class OwnedItemSetController {
    @Autowired
    private OwnedItemSetService ownedItemSetService;

    @DeleteMapping("/{username}/{name}")
    public ResponseEntity<String> deleteOwnedItemSet(@PathVariable String username, @PathVariable String name) {
        try {
            ownedItemSetService.deleteOwnedItemSet(username, name);
            return new ResponseEntity<>("Item set and associated items deleted successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping
    public ResponseEntity<List<OwnedItemSet>> getAllOwnedItemSets() {
        List<OwnedItemSet> ownedItemSets = ownedItemSetService.allOwnedItemSets();
        return new ResponseEntity<>(ownedItemSets, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OwnedItemSet> createOwnedItemSet(@RequestBody Map<String, Object> payload){
        String name = (String) payload.get("name");
        String description = (String) payload.get("description");
        String imageUrl = (String) payload.get("imageUrl");
        String clickedItemName = (String) payload.get("clickedItemName");
        String userName = (String) payload.get("username");
        String categoryName = (String) payload.get("categoryName");

        return new ResponseEntity<>(ownedItemSetService.createOwnedItemSet(name, description, imageUrl, clickedItemName, userName, categoryName), HttpStatus.CREATED);
    }

    @GetMapping("/{username}/{name}")
    public ResponseEntity<OwnedItemSet> getOwnedItemSetByName(@PathVariable String username, @PathVariable String name) {
        try {
            OwnedItemSet ownedItemSet = ownedItemSetService.getOwnedItemSetByName(username, name);

            if (ownedItemSet != null) {
                return new ResponseEntity<>(ownedItemSet, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
