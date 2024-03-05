package com.example.collectionorganizer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "owned_items")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OwnedItem {
    @Id
    private ObjectId id;
    private String name;
    private String imageUrl;
    private int quantity;
    private String ownedBy;
    public OwnedItem(String name, String imageUrl, int i, String username) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.quantity = i;
        this.ownedBy = username;
    }
}
