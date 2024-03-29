package com.Project.RestaurantFinder;

import org.junit.jupiter.api.*;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;


class RestaurantServiceTest {

    RestaurantService service = new RestaurantService();
    Restaurant restaurant;

    @BeforeEach
    public void beforeEachTest()
    {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Lemon Juice", 30);
        restaurant.addToMenu("Apple Juice", 110);
    }

    //SEARCHING
    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws restaurantNotFoundException {
        Restaurant resReturn = service.findRestaurantByName("Amelie's cafe");
        assertEquals(resReturn.getName(), restaurant.getName());
    }

    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {
        assertThrows(restaurantNotFoundException.class,()->service.findRestaurantByName("Bheema's cafe"));
    }
    //SEARCHING


    //ADMIN: ADDING & REMOVING RESTAURANTS
    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {


        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant("Amelie's cafe");
        assertEquals(initialNumberOfRestaurants-1, service.getRestaurants().size());
    }

    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException {


        assertThrows(restaurantNotFoundException.class,()->service.removeRestaurant("Pantry d'or"));
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1(){

        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales","Chennai",LocalTime.parse("12:00:00"),LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1,service.getRestaurants().size());
    }
    //ADMIN: ADDING & REMOVING RESTAURANTS
    //CALCULATE ORDER TOTAL
    @Test
    public void calculate_order_total_for_selected_items() throws itemNotFoundException, restaurantNotFoundException {
        List<String> selectedItemList = new ArrayList<String>();
        selectedItemList.add("Apple Juice");
        selectedItemList.add("Vegetable lasagne");
        assertEquals(379, service.calculateOrderTotal("Amelie's cafe", selectedItemList));
    }

    @Test
    public void calculate_order_total_for_no_selected_items() throws itemNotFoundException, restaurantNotFoundException
    {
        List<String> selectedItemList = new ArrayList<String>();
        assertEquals(0, service.calculateOrderTotal("Amelie's cafe", selectedItemList));
    }

    @Test
    public void calculate_order_total_for_item_does_not_exist_should_throw_exception()throws itemNotFoundException, restaurantNotFoundException    {
        List<String> selectedItemList = new ArrayList<String>();
        selectedItemList.add("Orange Juice");
        assertThrows(itemNotFoundException.class,()->service.calculateOrderTotal("Amelie's cafe", selectedItemList));
    }
    @Test
    public void calculate_order_total_for_zero_items_exist_in_restaurant_should_throw_exception()throws itemNotFoundException, restaurantNotFoundException
    {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Veera's cafe","Chennai",openingTime,closingTime);
        List<String> selectedItemList = new ArrayList<String>();
        selectedItemList.add("PineApple Juice");
        assertThrows(itemNotFoundException.class,()->service.calculateOrderTotal("Veera's cafe", selectedItemList));
    }

    //CALCULATE OREER TOTAL
}
