package com.engeto.orders;

import com.engeto.dishes.Dish;
import com.engeto.services.RestaurantException;

public class Table {
    private int numberOfTable;
    private int numberOfSeats;

    public Table(int numberOfTable, int numberOfSeats) throws RestaurantException {
        if (checkNumberOfTable()) {
            this.numberOfTable = numberOfTable;
            this.numberOfSeats = numberOfSeats;
            TableList.addTableToList(this);
        }
    }

    //region
    public int getNumberOfTable() {
        return numberOfTable;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public void setNumberOfTable(int numberOfTable) {
        this.numberOfTable = numberOfTable;
    }
//endregion


    public Boolean checkNumberOfTable() throws RestaurantException {
        if ((numberOfTable >= 1) || (numberOfTable <= 99)) return true;
        throw new RestaurantException("Číslo stolu musí být v rozmezí 1 - 99 ");
    }

}
