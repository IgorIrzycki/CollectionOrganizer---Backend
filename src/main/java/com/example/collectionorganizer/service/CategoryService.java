package com.example.collectionorganizer.service;

import com.example.collectionorganizer.model.Category;
import com.example.collectionorganizer.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> allCategories() {return categoryRepository.findAllProjectedBy();}

    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }
}
