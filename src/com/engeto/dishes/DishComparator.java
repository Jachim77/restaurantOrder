package com.engeto.dishes;

import java.util.Comparator;

public class DishComparator implements Comparator<Dish> {
    @Override
    public int compare(Dish dish1, Dish dish2) {
        return dish1.getDishCategory().compareTo(dish2.getDishCategory());
    }
}
