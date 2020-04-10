package com.loghme.loghme5b.repo.schedulers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loghme.loghme5b.repo.utils.Loghme;
import com.loghme.loghme5b.repo.utils.PartyFood;
import com.loghme.loghme5b.repo.utils.Restaurant;

import java.util.ArrayList;

public class PartySchedulerRunnable implements Runnable {
    @Override
    public void run() {
        ObjectMapper nameMapper = new ObjectMapper();
        ArrayList<Restaurant> tempRestaurants = null;
        try {
            String body = HTTPHandler.getUrlBody("http://138.197.181.131:8080/foodparty");
            body = body.replaceAll("menu", "partyFoods");
            body = body.replaceAll("price", "newPrice");
            body = body.replaceAll("oldPrice", "price");
            tempRestaurants = nameMapper.readValue(body, ArrayList.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Restaurant> partyRestaurants = nameMapper.convertValue(tempRestaurants, new TypeReference<ArrayList<Restaurant>>() { });
        Loghme.getInstance().addPartyRestaurants(partyRestaurants);
        for (Restaurant restaurant: partyRestaurants) {
            System.out.println("---restaurant name---" + restaurant.getName());
            System.out.println(restaurant.getId());
            System.out.println("---Party Foods---");
            for (PartyFood partyFood: restaurant.getPartyFoods()) {
                System.out.println(partyFood.getName());
                System.out.println(partyFood.getCount());
            }
        }
    }
}
