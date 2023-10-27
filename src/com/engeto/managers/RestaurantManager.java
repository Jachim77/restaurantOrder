package com.engeto.managers;

import com.engeto.dishes.Dish;
import com.engeto.dishes.DishComparator;
import com.engeto.orders.Order;
import com.engeto.orders.OrderComparatorOrderedTime;
import com.engeto.orders.OrderComparatorWaiter;
import com.engeto.orders.OrderList;
import com.engeto.services.RestaurantException;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RestaurantManager {
    //vrátí List - seznam všech objednávek
    public List getAllOrdersAsList() {
        return OrderList.getOrderSummary();
    }

    //vrátí List - seznam rozpracovaných objednávek
    public List getAllOpenOrdersAsList() {
        List<Order> allOpenOrders = new ArrayList<>();
        for (Order order : OrderList.getOrderSummary()) {
            if (order.getFulfilmentTime() == null) {
                allOpenOrders.add(order);
            }
        }
        return allOpenOrders;
    }

    //vrátí List - seznam všech obkjednávek za posledních x hodinn
    public List getAllOrdersDuringLastHours(int hours) {
        List<Order> allOrder = new ArrayList<>();
        for (Order order : OrderList.getOrderSummary()) {
            if (order.getOrderedTime().isAfter(LocalDateTime.now().minusHours(hours))) {
                allOrder.add(order);
            }
        }
        return allOrder;
    }

    //vrátí List objednávek jako String
    public String getListAsString(List<Order> orders) {
        String ordersAsString = "";
        for (Order order : orders) {
            ordersAsString += order + "\n";
        }
        return ordersAsString;
    }

    //setřídí objednávky dle zvoleného komparátoru
    public void sort(Comparator<Order> comparator) {
        Collections.sort(OrderList.getOrderSummary(), comparator);
    }

    //vrátí String - seznam všech objednávek
    public String getAllOrders() {
        List<Order> orders = getAllOrdersAsList();
        int numberOfAllOrders = orders.size();
        String allOrders = "Seznam všech objednávek: \n";
        allOrders += getListAsString(orders);
        allOrders += "Celkový počet všech objednávek: " + numberOfAllOrders + "\n";
        return allOrders;
    }

    //vrátí String - seznam rozpracovaných objednávek
    public String getAllOpenOrders() {
        List<Order> orders = getAllOpenOrdersAsList();
        int numberOfOpenOrders = orders.size();
        String allOpenOrders = "Seznam všech rozpracovaných objednávek: \n";
        allOpenOrders += getListAsString(orders);
        allOpenOrders += "Celkový počet aktuálně rozpracovaných objednávek: " + numberOfOpenOrders + "\n";
        return allOpenOrders;
    }


    //setřídí objednávky podle data zadání
    public String getOpenOrdersSortedByOrderedTime() {
        List<Order> listOfOrders = getAllOpenOrdersAsList();
        listOfOrders.sort(new OrderComparatorOrderedTime());
        return ("Rozpracované objednávky setříděné dle data zadání: \n" + getListAsString(listOfOrders));
    }

    //setřídí objednávky podle číšníka
    public String getOpenOrdresSortedbyWaiter() {
        List<Order> listOfOrders = getAllOpenOrdersAsList();
        listOfOrders.sort(new OrderComparatorWaiter());
        return ("Rozpracované objednávky setříděné dle číšníka: \n" + getListAsString(listOfOrders));
    }


    //vrátí String - celková cena objednávek pro jednotlivé číšníky
    public String getTotalOrdersPriceForEachWaiter(int hours) {
        try {
            List<Order> listOforders = getAllOrdersDuringLastHours(hours);
            listOforders.sort(new OrderComparatorWaiter());
            Order firstOrder = listOforders.get(0);
            int lastId = firstOrder.getWaiterId();
            int currentId;
            int i = 0;
            BigDecimal price = BigDecimal.valueOf(0);
            String ordersPriceAtAWaiter = "Celková cena všech objednávek pro jednotlivé číšníky za poslední " + hours + " hodin(y): \n";
            for (Order order : listOforders) {
                currentId = order.getWaiterId();
                BigDecimal currentPrice = new BigDecimal(String.valueOf(order.getDish().getPrice()));
                if (currentId == lastId) {
                    price = price.add(currentPrice);
                    i++;
                } else {
                    ordersPriceAtAWaiter = ordersPriceAtAWaiter + "číšník ID:" + lastId + " =>  " + price + ",-Kč, (počet objednávek: " + i + ") \n";
                    price = BigDecimal.valueOf(0);
                    price = price.add(currentPrice);
                    lastId = currentId;
                    i = 1;
                }
            }
            ordersPriceAtAWaiter = ordersPriceAtAWaiter + "číšník ID:" + lastId + " =>  " + price + ",-Kč, (počet objednávek: " + i + ") \n";
            return ordersPriceAtAWaiter;
        } catch (IndexOutOfBoundsException e) {
            return "Celková cena všech objednávek pro jednotlivé číšníky za poslední" + hours + " hodin(y): \n" +
                    "Neexistují žádné objednávky, celkovou cenu nelze spočítat " + "\n";
        }
    }

    // vrátí String - průměrnou dobu zpracování obkednávek za posledních x hodin
    public String getAverageOfOrderTime(int hours) {
        List<Order> listOfOrders = new ArrayList<>(getAllOrdersDuringLastHours(hours));
        int totalProcesingMinutes = 0;
        int numberOfOrders = 0;
        for (Order order : listOfOrders) {
            if (order.getFulfilmentTime() != null) {
                Duration processingTime = Duration.between(order.getOrderedTime(), order.getFulfilmentTime());
                long processingMinutes = processingTime.toMinutes();
                totalProcesingMinutes += processingMinutes;
                numberOfOrders++;
            }
        }
        if (numberOfOrders == 0) {
            return ("Průměrná doba zpracování objednávek za poslední " + hours + " hodin(y):" + "\n" +
                    "Nenalezeny žádné uzavřené objednávky, průměrnou dobu nelze spočítat" + "\n");
        }
        int averageTime = (totalProcesingMinutes / numberOfOrders);
        String message = "Průměrná doba zpracování objednávek za poslední " + hours + " hodin(y): " + averageTime + " minut(y) \n" +
                "Celkem uzavřených objednávek: " + numberOfOrders;
        return message;
    }

    // vrátí String - seznam všech jídel, které byly obejdnány za posledních x hodin
    public String getAllOrderdDishes(int hours) {
        List<Order> allOrdersL = new ArrayList<>(getAllOrdersAsList());
        Set<Dish> allDishesS = new HashSet<>();
        for (Order order : allOrdersL) {
            allDishesS.add(order.getDish());
        }
        List<Dish> allDishes = new ArrayList<>(allDishesS);
        Collections.sort(allDishes, new DishComparator());
        String allOrderedDishes = "Seznam všech objednaných jídel za poslední " + hours + " hodin(y): \n";
        for (Dish dish : allDishes) {
            allOrderedDishes += dish.getTitle() + "\n";
            ;
        }
        return allOrderedDishes;
    }

    public String getAllOrdersFromOneOrderList(int numberOfOrderList) {
        List<Order> orders = getAllOrdersAsList();
        int numberOfTable = 0;
        int i;
        String ordersToATable = "** Objednávky pro stůl č.";
        for (i = 0; i < orders.size() && numberOfTable == 0; i++) {
            if (orders.get(i).getOrderList() == numberOfOrderList) {
                numberOfTable = orders.get(i).getTableId();
            }
        }
        if (numberOfTable == 0) {
            return ("Nepodařilo se najít žádné objednávky k id: " + numberOfOrderList) + "\n";
        }
        String numberOfTableAsString;
        if ((numberOfTable >= 1) && (numberOfTable <= 9)) {
            numberOfTableAsString = " " + (numberOfTable);
        } else {
            numberOfTableAsString = String.valueOf(numberOfTable);
        }
        ordersToATable += numberOfTableAsString + " **\n" + "****\n";
        for (Order order : orders) {
            if (order.getOrderList() == numberOfOrderList) {
                BigDecimal price = (order.getDish().getPrice()).multiply(BigDecimal.valueOf(order.getNumberOfPieces()));
                String orderStart = order.getOrderedTime().format(DateTimeFormatter.ofPattern("HH:mm"));
                String orderFinish;
                if (order.getFulfilmentTime() == null) {
                    orderFinish = "neuzavřeno";
                } else orderFinish = order.getFulfilmentTime().format(DateTimeFormatter.ofPattern("HH:mm"));
                ordersToATable += order.getOrderId() + ". " + order.getDish().getTitle() + " " + order.getNumberOfPieces() + "x (" + price + "Kč):\t" + orderStart + "-" + orderFinish + "\tčíšník č. " + order.getWaiterId() + "\n";
            }
        }
        ordersToATable += "******\n";
        return ordersToATable;
    }

}


