package com.engeto.orders;

import java.util.Comparator;

public class OrderComparatorOrderedTime implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        return o1.getOrderedTime().compareTo(o2.getOrderedTime());
    }
}
