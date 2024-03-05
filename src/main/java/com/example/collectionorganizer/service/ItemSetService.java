package com.example.collectionorganizer.service;

import com.example.collectionorganizer.model.Category;
import com.example.collectionorganizer.model.ItemSet;
import com.example.collectionorganizer.repository.CategoryRepository;
import com.example.collectionorganizer.repository.ItemSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ItemSetService {
    @Autowired
    private ItemSetRepository itemSetRepository;

    public ItemSet getItemSetByName(String name) {
        return itemSetRepository.findByName(name);
    }

    public List<ItemSet> getItemSetsWithoutItemsByCategoryName(String categoryName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return itemSetRepository.findByCategoryName(categoryName, pageable);
    }
}
