package com.loghme.loghme5b.repo.schedulers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loghme.loghme5b.repo.utils.*;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.lang.*;

public class CouriersScheduler extends TimerTask {
    private ArrayList<Courier> couriers = null;
    private Order order;

    public CouriersScheduler(Order order) {this.order = order;}

    public void initCouriers() {
        couriers = null;
        ObjectMapper nameMapper = new ObjectMapper();
        ArrayList<Courier> tempCouriers = null;
        try {
            String body = HTTPHandler.getUrlBody("http://138.197.181.131:8080/deliveries");
            tempCouriers = nameMapper.readValue(body, ArrayList.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        couriers = nameMapper.convertValue(tempCouriers, new TypeReference<ArrayList<Courier>>() { });
    }

    public Courier getClosestCourier() {
        Courier bestCourier = couriers.get(0);
        double d;
        Location restaurantLocation = order.getRestaurant().getLocation();
        double minTime = Double.MAX_VALUE, time;

        for (Courier courier: couriers) {
            d = Math.sqrt(Math.pow(restaurantLocation.getX(), 2) + Math.pow(restaurantLocation.getY(), 2));
            d += Math.sqrt(Math.pow(restaurantLocation.getX() - courier.getLocation().getX(), 2) + Math.pow(restaurantLocation.getY() - courier.getLocation().getY(), 2));
            time = d / courier.getVelocity();
            if (time < minTime) {
                minTime = time;
                bestCourier = courier;
            }
        }
        return bestCourier;
    }

    public void run() {
        Location restaurantLocation = order.getRestaurant().getLocation();
        initCouriers();
        Courier closestCourier;
        if (couriers != null && couriers.size() != 0) {
            cancel();
            closestCourier = getClosestCourier();
            try {
                double time;
                time = Math.sqrt(Math.pow(restaurantLocation.getX(), 2) + Math.pow(restaurantLocation.getY(), 2));
                time += Math.sqrt(Math.pow(restaurantLocation.getX() - closestCourier.getLocation().getX(), 2) + Math.pow(restaurantLocation.getY() - closestCourier.getLocation().getY(), 2));
                time = time / closestCourier.getVelocity();

                order.setRemainingTime(time);
                order.setStatus("delivering");
                order.setDeliveryBeginTime(System.nanoTime());

                Timer timer = new Timer();
                TimerTask task = new ChangeCourierStatus(order);
                timer.schedule(task, (long) time*1000);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            couriers = null;
        }
    }
}
