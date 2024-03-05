package com.example.collectionorganizer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "trade_offers")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeOffer {
    @Id
    private String id;
    private String itemSetName;
    private String username1;
    private String username2;
    private List<TradeItem> selectedItemsToTrade;
    private List<TradeItem> selectedWantedItems;
    private boolean isDenied;
    private boolean isAccepted;
}
