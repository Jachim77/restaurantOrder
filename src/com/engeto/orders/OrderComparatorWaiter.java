package com.engeto.orders;

import java.util.Comparator;

public class OrderComparatorWaiter implements Comparator<Order> {

    @Override
    public int compare(Order o1, Order o2) {
        return String.valueOf(o1.getWaiterId()).compareTo(String.valueOf(o2.getWaiterId()));
    }
}
