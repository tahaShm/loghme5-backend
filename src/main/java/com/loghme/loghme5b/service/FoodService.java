package com.loghme.loghme5b.service;

import com.loghme.loghme5b.repo.utils.Food;
import com.loghme.loghme5b.repo.utils.Loghme;
import com.loghme.loghme5b.repo.utils.Restaurant;
import org.json.JSONException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FoodService {
    private Loghme loghme = Loghme.getInstance();

    @RequestMapping(value = "/food/{id}", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addFood(
            @PathVariable(value = "id") String id,
            @RequestParam(value = "foodName") String foodName,
            @RequestParam(value = "count") int count) {
        try {
            Restaurant restaurant = loghme.getRestaurantById(id);
            loghme.addToCart(restaurant, foodName, count);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/food/{id}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteFood(
            @PathVariable(value = "id") String id,
            @RequestParam(value = "foodName") String foodName,
            @RequestParam(value = "count") int count) {
        try {
            Restaurant restaurant = loghme.getRestaurantById(id);
            loghme.removeFromCart(restaurant, foodName, count);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(null);
    }
}
