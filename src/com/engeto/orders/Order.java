package com.engeto.orders;

import com.engeto.dishes.Dish;

import java.awt.*;
import java.time.LocalTime;
import java.util.*;

public class Orders {
    private static int nextId = 1;
    private int id;
    private LocalTime orderedTime;
    private List<Dish> orderedDishes = new ArrayList<>();
    private LocalTime fulfilmentTime;
    private Waiter waiter;
    
}
