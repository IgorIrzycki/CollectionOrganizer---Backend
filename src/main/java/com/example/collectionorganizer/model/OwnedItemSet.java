package com.example.collectionorganizer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection = "owned_item_sets")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OwnedItemSet {
    @Id
    private ObjectId id;
    private String name;
    private String description;
    private String imageUrl;
    private String categoryName;
    private String ownedBy;
    private int size;

    @DocumentReference
    private List<OwnedItem> ownedItems;
}
