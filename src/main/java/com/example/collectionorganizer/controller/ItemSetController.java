package com.example.collectionorganizer.controller;

import com.example.collectionorganizer.model.ItemSet;
import com.example.collectionorganizer.service.ItemSetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/item-set")
@RequiredArgsConstructor
public class ItemSetController {
    @Autowired
    private ItemSetService itemSetService;

    @GetMapping("/{name}")
    public ResponseEntity<ItemSet> getItemSetByName(@PathVariable String name) {
        try {
            ItemSet itemSet = itemSetService.getItemSetByName(name);

            if (itemSet != null) {
                return new ResponseEntity<>(itemSet, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/by-category/{categoryName}")
    public ResponseEntity<List<ItemSet>> getItemSetsWithoutItemsByCategoryName(
            @PathVariable String categoryName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            List<ItemSet> itemSets = itemSetService.getItemSetsWithoutItemsByCategoryName(categoryName, page, size);
            return new ResponseEntity<>(itemSets, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
