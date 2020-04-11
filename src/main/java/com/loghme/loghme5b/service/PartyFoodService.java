package com.loghme.loghme5b.service;

import com.loghme.loghme5b.BadRequestException;
import com.loghme.loghme5b.repo.utils.FoodInOrder;
import com.loghme.loghme5b.repo.utils.Loghme;
import com.loghme.loghme5b.repo.utils.PartyFood;
import com.loghme.loghme5b.repo.utils.Restaurant;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class PartyFoodService {
    private Loghme loghme = Loghme.getInstance();

    @RequestMapping(value = "/partyFood/{id}", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<FoodInOrder> addFood(
            @PathVariable(value = "id") String id,
            @RequestParam(value = "foodName") String foodName,
            @RequestParam(value = "count") int count) {
        try {
            Restaurant restaurant = loghme.getRestaurantById(id);
            loghme.addToCart(restaurant, foodName, count, true);
        }
        catch (Exception e) {
            throw new BadRequestException();
        }
        return loghme.getCustomer().getCurrentOrder().getFoodsInOrder();
    }

    @RequestMapping(value = "/partyFood/{id}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<FoodInOrder> deleteFood(
            @PathVariable(value = "id") String id,
            @RequestParam(value = "foodName") String foodName,
            @RequestParam(value = "count") int count) {
        try {
            Restaurant restaurant = loghme.getRestaurantById(id);
            loghme.removeFromCart(restaurant, foodName, count, true);
        }
        catch (Exception e) {
            throw new BadRequestException();
        }
        return loghme.getCustomer().getCurrentOrder().getFoodsInOrder();
    }

    @RequestMapping(value = "/partyFood", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<PartyFood> getFoods() {
        ArrayList<PartyFood> foods = null;
        try {
            foods = loghme.getPartyFoods();
        }
        catch (Exception e) {
            throw new BadRequestException();
        }
        return foods;
    }
}
