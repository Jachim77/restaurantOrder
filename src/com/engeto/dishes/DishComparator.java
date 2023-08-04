package com.engeto.orders;

import java.util.Comparator;

public class DishComparator implements Comparator<Dish> {
    @Override
    public int compare(Dish o1, Dish o2) {
        return o1.getDishCategory().compareTo(o2.getDishCategory());
    }
}
