package com.engeto.orders;

import com.engeto.services.RestaurantSettings;
import com.engeto.services.RestaurantException;

import java.io.File;
import java.util.*;

public class Waiter implements Comparable<Waiter> {
    private static int nextID = 1;
    private int id;
    private String name;
    private String surname;
    private static final File file = new File(RestaurantSettings.getInputFileWaiters());

    public Waiter(int id, String name, String surname) throws RestaurantException {
        if (WaiterList.allIdS().contains(id)) {
            throw new RestaurantException("Zadané id již existuje");
        }
        this.id = id;
        this.name = name;
        this.surname = surname;
        WaiterList.addWaiterToList(this);
    }

    public Waiter(String name, String surname) throws RestaurantException {
        this(WaiterList.checkNextid(), name, surname);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getId() {
        return id;
    }


    @Override
    public String toString() {
        return "Waiter {" +
                "id: " + id +
                ", jméno: " + name +
                ", příjmení: " + surname +
                "}";
    }

    @Override
    public int compareTo(Waiter waiter) {
        return getSurname().compareTo(waiter.getSurname());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Waiter waiter = (Waiter) o;
        return id == waiter.id && name.equals(waiter.name) && surname.equals(waiter.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname);
    }
}
