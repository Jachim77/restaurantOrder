package com.engeto.orders;

import com.engeto.dishes.Dish;
import com.engeto.dishes.DishMenu;
import com.engeto.services.RestaurantException;
import com.engeto.services.RestaurantSettings;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Order {
    private static int nextId = 1;
    private int orderId;
    private int orderList;
    private int tableId;
    private LocalDateTime orderedTime;
    private Dish dish;
    private int indexMenu;
    private int numberOfPieces;
    private LocalDateTime fulfilmentTime;
    private int waiterId;
    private static final File file = new File(RestaurantSettings.getInputFileOrders());

    public Order(int orderList, int tableId, int indexMenu, int numberOfPieces, int waiterId) throws RestaurantException {
        if (indexMenu >= (DishMenu.getMenu().size())) {
            throw new RestaurantException("Objednávka nebyla vytvořena, zadaný index jídla v menu neexistuje: " + indexMenu);
        }
        if (checkLastOrderId() >= nextId) {
            nextId = checkLastOrderId() + 1;
        }
        this.orderId = nextId++;
        this.orderList = orderList;
        this.tableId = tableId;
        this.orderedTime = LocalDateTime.now();
        this.dish = DishMenu.getMenu().get(indexMenu);
        this.numberOfPieces = numberOfPieces;
        this.waiterId = waiterId;
    }

    public Order(int orderId, int orderList, int tableId, LocalDateTime orderedTime, int indexMenu, int numberOfPieces, LocalDateTime fulfilmentTime, int waiterId) throws RestaurantException {
        if (checkIfIdAlreadyExists(orderId)) {
            throw new RestaurantException("Uvedené id objednávky již existuje: " + orderId);
        }
        if (DishMenu.getMenu().isEmpty()) {
            throw new RestaurantException("Menu je prázdné, objednávku nelze vytvořit");

        }
        this.orderId = orderId;
        this.orderList = orderList;
        this.tableId = tableId;
        this.orderedTime = orderedTime;
        this.dish = DishMenu.getMenu().get(indexMenu);
        this.numberOfPieces = numberOfPieces;
        this.fulfilmentTime = fulfilmentTime;
        this.waiterId = waiterId;
    }


//region

    public int getOrderId() {
        return orderId;
    }

    public int getOrderList() {
        return orderList;
    }

    public int getTableId() {
        return tableId;
    }

    public LocalDateTime getOrderedTime() {
        return orderedTime;
    }

    public void setOrderedTime(LocalDateTime orderedTime) {
        this.orderedTime = orderedTime;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getIndexMenu() {
        return indexMenu;
    }

    public void setIndexMenu(int indexMenu) {
        this.indexMenu = indexMenu;
    }

    public int getNumberOfPieces() {
        return numberOfPieces;
    }

    public void setNumberOfPieces(int numberOfPieces) {
        this.numberOfPieces = numberOfPieces;
    }

    public LocalDateTime getFulfilmentTime() {
        return fulfilmentTime;
    }

    public void setFulfilmentTime(LocalDateTime localTime) {
        this.fulfilmentTime = localTime;
        System.out.println("Objednávka id:" + orderId + " byla uzavřena " + localTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd : HH:mm")));
    }

    public int getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(int waiterId) {
        this.waiterId = waiterId;
    }

//endregion

    public int checkLastOrderId() throws RestaurantException {
        if (file.exists()) {
            int lineNumber = 0;
            String line;
            String[] items;
            List<Integer> idSL = new ArrayList<>();
            Integer[] idSA;
            try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)))) {
                while (scanner.hasNextLine()) {
                    lineNumber++;
                    line = scanner.nextLine();
                    items = line.split(RestaurantSettings.getItemsSeparator());
                    idSL.add(Integer.parseInt(items[0]));
                }
                idSA = idSL.toArray(idSL.toArray(new Integer[0]));
                Arrays.sort(idSA, Collections.reverseOrder());
                if (idSA.length == 0) {
                    return 0;
                }
                return idSA[0];
            } catch (NumberFormatException e) {
                throw new RestaurantException("Chybný údaj na řádku" + lineNumber + "\n" + e.getLocalizedMessage());
            } catch (FileNotFoundException e) {
                throw new RestaurantException("Nenalezen soubor waiters.txt" + "\n" + e.getLocalizedMessage());
            }
        } else return 0;
    }

    public Boolean checkIfIdAlreadyExists(int checkId) {
        for (Order order : OrderList.getOrderSummary()) {
            if (order.getOrderId() == checkId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String fulfilmentTimeF = "null";
        if (fulfilmentTime != null) {
            fulfilmentTimeF = fulfilmentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd : HH:mm"));
        }
        return "id: " + orderId +
                ", order list: " + orderList +
                ", table id: " + tableId +
                ", ordered time: " + orderedTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd : HH:mm")) +
                ", dish: " + dish.getTitle() +
                ", number of pieces: " + numberOfPieces +
                ", total price:" + dish.getPrice().multiply(BigDecimal.valueOf(numberOfPieces)) +
                ", fulfilment time: " + fulfilmentTimeF +
                ", waiter: " + waiterId;
    }

    //vrátí číšníka patřícího k objednávce
    public Waiter findWaiter() throws RestaurantException {
        Waiter waiter = WaiterList.getWaiterFromList(getWaiterId());
        return waiter;
    }


}
