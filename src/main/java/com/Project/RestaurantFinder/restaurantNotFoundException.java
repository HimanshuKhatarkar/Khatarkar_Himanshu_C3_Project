package com.Project.RestaurantFinder;

public class restaurantNotFoundException extends Throwable{
    public restaurantNotFoundException(String restaurantName) {
        super(restaurantName);
    }
}
