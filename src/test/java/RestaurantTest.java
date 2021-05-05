import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

import static java.time.Instant.ofEpochMilli;
import static org.mockito.Mockito.spy;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mock;
import org.mockito.Mockito;

class RestaurantTest {
    Restaurant restaurant;
    
    @BeforeEach
    public void Test_restaurant_class() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne",269);
    }
    //REFACTOR ALL THE REPEATED LINES OF CODE

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        LocalTime curTime = LocalTime.parse("12:00:00");
        Restaurant spyRestaurant = spy(restaurant);

        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(curTime);

        assertTrue(spyRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        LocalTime curTime = LocalTime.parse("22:00:01");
        Restaurant spyRestaurant = spy(restaurant);

        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(curTime);

        assertFalse(spyRestaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //<<<<<<<<<<<<<<<<<<<<TDD - Total Cost of Order >>>>>>>>>>>>>>>>>>
    @Test
    public void total_cost_of_order_to_be_sum_of_two_items_selected(){
        List<String> selectedMenuItems = new ArrayList<String>();
        selectedMenuItems.add(restaurant.getMenu().get(0).getName());
        selectedMenuItems.add(restaurant.getMenu().get(1).getName());
        int testTotalCost = restaurant.totalCostOfOrder(selectedMenuItems);
        assertEquals(388,testTotalCost);
    }

    @Test
    public void total_cost_of_order_to_be_zero_if_no_item_selected(){
        List<String> selectedMenuItems = new ArrayList<String>();
        int testTotalCost = restaurant.totalCostOfOrder(selectedMenuItems);
        assertEquals(0,testTotalCost);
    }

    //<<<<<<<<<<<<<<<<<<<<TDD - Total Cost of Order >>>>>>>>>>>>>>>>>>
}