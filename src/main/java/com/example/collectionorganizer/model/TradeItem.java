package com.example.collectionorganizer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "trade_items")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeItem {
    @Id
    private ObjectId id;
    private String itemName;
    private int quantityUser;
    private int quantityOtherUser;
}
