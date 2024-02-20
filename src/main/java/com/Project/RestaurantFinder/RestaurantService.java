package com.Project.RestaurantFinder;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RestaurantService {
    private static List<Restaurant> restaurants = new ArrayList<>();

    public Restaurant findRestaurantByName(String restaurantName) throws restaurantNotFoundException{
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().equals(restaurantName)) {
                return restaurant;
            }
        }
        throw new restaurantNotFoundException(restaurantName +" does not exists");
        //return null;
    }


    public Restaurant addRestaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        Restaurant newRestaurant = new Restaurant(name, location, openingTime, closingTime);
        restaurants.add(newRestaurant);
        return newRestaurant;
    }

    public Restaurant removeRestaurant(String restaurantName) throws restaurantNotFoundException {
        Restaurant restaurantToBeRemoved = findRestaurantByName(restaurantName);
        if(null == restaurantToBeRemoved)
            throw new restaurantNotFoundException(restaurantName +" does not exists");
        restaurants.remove(restaurantToBeRemoved);
        return restaurantToBeRemoved;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }
    public int calculateOrderTotal(String restaurantName, List<String> selectedItemList) throws itemNotFoundException, restaurantNotFoundException
    {
        int totalOrderedAmount = 0;
        Restaurant restaurantFound = findRestaurantByName(restaurantName);
        if(null == restaurantFound)
            throw new restaurantNotFoundException(restaurantName +" does not exists");
        List<Item> menuList = restaurantFound.getMenu();
        if(menuList.isEmpty())
            throw new itemNotFoundException("No items are there in restaurant");
        for(String selectedItem: selectedItemList)
        {
            boolean selectedItemFound = false;
            for(Item menuItem: menuList)
            {
                if (selectedItem.contains(menuItem.getName())) {
                    totalOrderedAmount += menuItem.getPrice();
                    selectedItemFound = true;
                    break;
                }
            }
            if(!selectedItemFound)
            {
                throw new itemNotFoundException(selectedItem + "is not found at " + restaurantName);
            }
        }
        return totalOrderedAmount;
    }
}
