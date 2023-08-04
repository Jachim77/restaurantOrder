package com.engeto.orders;

import com.engeto.services.RestaurantException;
import com.engeto.services.RestaurantSettings;

import java.io.*;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class OrderList {
    private static List<Order> orderSummary = new ArrayList<>();
    private int orderListId;
    private int tableId;
    private List<Order> orderListAtTable = new ArrayList<>();
    private String comment;
    private Boolean valid;
    private static File file = new File(RestaurantSettings.getInputFileOrders());

    public OrderList(int tableId) {
        this.orderListId = checkNextOrderListId();
        this.tableId = tableId;
        this.valid = true;
    }

    //region
    public static List<Order> getOrderSummary() {
        return orderSummary;
    }

    public static void setOrderSummary(List<Order> orderSummary) {
        OrderList.orderSummary = orderSummary;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTable(int tableId) {
        this.tableId = tableId;
    }

    public List<Order> getOrderListAtTable() {
        return orderListAtTable;
    }

    public void setOrderListAtTable(List<Order> orderListAtTable) {
        this.orderListAtTable = orderListAtTable;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public static File getFile() {
        return file;
    }

    //endregion
    //vytvoří objednávku a přiřadí ji do seznamu objednávek k danému stolu
    public void addOrderToOrderList(int index, int numberOfPieces, int waiterId) throws RestaurantException {
        Order order = new Order(orderListId, tableId, index, numberOfPieces, waiterId);
        orderListAtTable.add(order);
        orderSummary.add(order);
    }

    //vypíše seznam objednávek pro daný stůl
    public String getAllOrderToATable() {
        String c;
        if ((tableId >= 1) && (tableId <= 9)) {
            c = " " + String.valueOf(tableId);
        } else {
            c = String.valueOf(tableId);
        }

        String a = "** Objednávky pro stůl č." + c + " ** \n" + "****\n";
        for (Order order : orderListAtTable) {
            BigDecimal b = (order.getDish().getPrice()).multiply(BigDecimal.valueOf(order.getNumberOfPieces()));
            String orderStart = order.getOrderedTime().format(DateTimeFormatter.ofPattern("HH:mm"));
            String orderFinish;
            if (order.getFulfilmentTime() == null) {
                orderFinish = "neuzavřeno";
            } else orderFinish = order.getFulfilmentTime().format(DateTimeFormatter.ofPattern("HH:mm"));
            a += +order.getOrderId() + ". " + order.getDish().getTitle() + " " + order.getNumberOfPieces() + "x (" + b + " Kč):\t" + orderStart + "-" + orderFinish + "\tčíšník č. " + order.getWaiterId() + "\n";
        }
        ;
        a += "******\n";
        return a;
    }

    //vrátí objednávku ze seznamu na určité pozici
    public Order getOrderFromList(int a) throws RestaurantException {
        for (Order order : orderListAtTable) {
            if (order.getOrderId() == a) {
                return order;
            }
        }
        throw new RestaurantException("Nepodařilo se nalézt oboedjnávku s uvedeným id: " + a);
    }

    //vypíše všechny objednávky
    public static String getAllOrders() {
        String b = "Pčehled všech objednávek:  \n";
        for (Order order : orderSummary) {
            b = b + order + "\n";
        }
        return b;
    }

    //načte objednávky ze souboru
    public static void addAllOrdersFromFile(File filename) throws RestaurantException {
        int lineNumber = 0;
        String[] items = new String[0];
        String line = "";
        if (filename.exists()) {
            try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filename)))) {
                while (scanner.hasNextLine()) {
                    lineNumber++;
                    line = scanner.nextLine();
                    items = line.split(RestaurantSettings.getItemsSeparator());
                    if (items.length == 8) {
                        if (items[6].equals("null")) {
                            Order order = new Order(Integer.parseInt(items[0]), Integer.parseInt(items[1]), Integer.parseInt(items[2]), LocalDateTime.parse(items[3]), Integer.parseInt(items[4]),
                                    Integer.parseInt(items[5]), null, Integer.parseInt(items[7]));
                            orderSummary.add(order);
                        } else {
                            Order order = new Order(Integer.parseInt(items[0]), Integer.parseInt(items[1]), Integer.parseInt(items[2]), LocalDateTime.parse(items[3]), Integer.parseInt(items[4]),
                                    Integer.parseInt(items[5]), LocalDateTime.parse(items[6]), Integer.parseInt(items[7]));
                            orderSummary.add(order);
                        }
                    } else {
                        throw new RestaurantException("Špatný počet položek na řádku " + lineNumber);
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RestaurantException("Nepodařilo se nalézt soubor " + file + "\n" + e.getLocalizedMessage());
            } catch (NumberFormatException e) {
                throw new RestaurantException("Špatně zadaná jedna nebo více hodnot v souboru " + filename + " na řádku " + lineNumber + "\n" + e.getLocalizedMessage());
            } catch (DateTimeException e) {
                throw new RestaurantException("Špatně zadaná jedna nebo více hodnot v souboru " + filename + " na řádku " + lineNumber + "\n" + e.getLocalizedMessage());
            }
        }
    }

    //uloží objednávky do souboru
    public static void saveAllFromOrderSummaryToFile(String FileName) throws RestaurantException {
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(FileName)))) {
            for (Order order : orderSummary) {
                String record = order.getOrderId() + RestaurantSettings.getItemsSeparator()
                        + order.getOrderList() + RestaurantSettings.getItemsSeparator()
                        + order.getTableId() + RestaurantSettings.getItemsSeparator()
                        + order.getOrderedTime() + RestaurantSettings.getItemsSeparator()
                        + order.getIndexMenu() + RestaurantSettings.getItemsSeparator()
                        + order.getNumberOfPieces() + RestaurantSettings.getItemsSeparator()
                        + order.getFulfilmentTime() + RestaurantSettings.getItemsSeparator()
                        + order.getWaiterId() + RestaurantSettings.getItemsSeparator();
                printWriter.println(record);
            }
        } catch (IOException e) {
            throw new RestaurantException("Došlo k chybě při zápisu do souboru" + e.getLocalizedMessage());
        }
    }


    //vrátí aktuální orderlisId id
    public int checkNextOrderListId() {
        if (orderSummary.isEmpty()) {
            return 1;
        }
        ;
        List<Integer> ListIds = new ArrayList<>();
        for (Order order : orderSummary) {
            ListIds.add(order.getOrderList());
        }
        return Collections.max(ListIds) + 1;
    }


}


