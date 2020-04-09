package com.loghme.loghme5b.repo.utils;

import java.util.HashMap;
import java.util.Map;

public class Order {
    private HashMap<Food, Integer> foods = new HashMap<>();
    private int id;
    private double remainingTime;
    private String status;
    Restaurant restaurant;
    long deliveryBeginTime;

    public Order(int id, Restaurant restaurant) {
        this.id = id;
        this.restaurant = restaurant;
    }

    public void setId(int id) {this.id = id;}

    public void setRemainingTime(double remainingTime) {this.remainingTime = remainingTime;}

    public void setStatus(String status) {this.status = status;}

    public HashMap<Food, Integer> getFoods() {return foods;}

    public int getId() {return id;}

    public double getRemainingTime() {return remainingTime;}

    public String getStatus() {return status;}

    public Restaurant getRestaurant() {return restaurant;}

    public void setDeliveryBeginTime(long time) {deliveryBeginTime = time;}

    public long getDeliveryBeginTime() {return deliveryBeginTime;}

    public void addFood(Food food) {
        if (foods.containsKey(food))
            foods.put(food, foods.get(food) + 1);
        else
            foods.put(food, 1);
    }

    public int overallPrice() {
        int price = 0;
        for (Map.Entry<Food, Integer> entry: foods.entrySet()) {
            price += entry.getKey().getPrice() * entry.getValue();
        }
        return price;
    }
}
