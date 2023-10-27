package com.engeto.orders;

import java.util.Comparator;

public class OrderComparatorOrderedTime implements Comparator<Order> {
    @Override
    public int compare(Order order1, Order order2) {
        return order1.getOrderedTime().compareTo(order2.getOrderedTime());
    }
}
