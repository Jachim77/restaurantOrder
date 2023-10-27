package com.engeto.orders;

import java.util.Comparator;

public class OrderComparatorWaiter implements Comparator<Order> {

    @Override
    public int compare(Order order1, Order order2) {
        return String.valueOf(order1.getWaiterId()).compareTo(String.valueOf(order2.getWaiterId()));
    }
}
