package com.example.collectionorganizer.controller;
import com.example.collectionorganizer.model.TradeOffer;
import com.example.collectionorganizer.service.TradeOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/trade-offers")
public class TradeOfferController {
    @Autowired
    private TradeOfferService tradeOfferService;

    @PostMapping("/confirm/{offerId}")
    public ResponseEntity<?> confirmTradeOffer(@PathVariable String offerId) {
        try {
            List<String> missingItems = tradeOfferService.confirmTradeOffer(offerId);

            if (missingItems.isEmpty()) {
                return ResponseEntity.ok("Trade offer confirmed successfully");
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("error", "Not enough items available for confirmation");
                response.put("missingItems", missingItems);
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to confirm trade offer");
        }
    }



    @PostMapping("/reject/{offerId}")
    public ResponseEntity<String> rejectTradeOffer(@PathVariable String offerId) {
        try {
            tradeOfferService.rejectTradeOffer(offerId);
            return ResponseEntity.ok("Trade offer rejected successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to reject trade offer");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTradeOfferById(@PathVariable String id) {
        try {
            tradeOfferService.deleteTradeOfferById(id);
            return new ResponseEntity<>("Trade offer deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete trade offer", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<String> createTradeOffer(@RequestBody TradeOffer tradeOffer) {
        try {
            tradeOfferService.createTradeOffer(tradeOffer);
            return new ResponseEntity<>("Trade offer created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create trade offer", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/userTradeOffers/{username}")
    public ResponseEntity<List<TradeOffer>> getUserTradeOffers(@PathVariable String username) {
        List<TradeOffer> userTradeOffers = tradeOfferService.getUserTradeOffers(username);
        return new ResponseEntity<>(userTradeOffers, HttpStatus.OK);
    }

    @GetMapping("/otherUserTradeOffers/{username}")
    public ResponseEntity<List<TradeOffer>> getOtherUserTradeOffers(@PathVariable String username) {
        List<TradeOffer> otherUserTradeOffers = tradeOfferService.getOtherUserTradeOffers(username);
        return new ResponseEntity<>(otherUserTradeOffers, HttpStatus.OK);
    }
}

