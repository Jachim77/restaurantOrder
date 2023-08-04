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
    public List getAllOrdersDuringLastHours(int h) {
        List<Order> allOrder = new ArrayList<>();
        for (Order order : OrderList.getOrderSummary()) {
            if (order.getOrderedTime().isAfter(LocalDateTime.now().minusHours(h))) {
                allOrder.add(order);
            }
        }
        return allOrder;
    }

    //vrátí List objednávek jako String
    public String getListAsString(List<Order> orders) {
        String ordersAsString = "";
        for (Order order : orders) {
            ordersAsString = ordersAsString + order + "\n";
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
        int f = orders.size();
        String allOrders = "Seznam všech objednávek: \n";
        allOrders += getListAsString(orders);
        allOrders += "Celkový počet všech objednávek: " + f + "\n";
        return allOrders;
    }

    //vrátí String - seznam rozpracovaných objednávek
    public String getAllOpenOrders() {
        List<Order> orders = getAllOpenOrdersAsList();
        int c = orders.size();
        String allOpenOrders = "Seznam všech rozpracovaných objednávek: \n";
        allOpenOrders = allOpenOrders + getListAsString(orders);
        allOpenOrders = allOpenOrders + "Celkový počet aktuálně rozpracovaných objednávek: " + c + "\n";
        return allOpenOrders;
    }

    //setřídí objednávky podle data zadání
    public String getOpenOrdersSortedByOrderčedTime() {
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
    public String getTotalOrdersPriceForEachWaiter(int h) {
        List<Order> listOforders = getAllOrdersDuringLastHours(h);
        listOforders.sort(new OrderComparatorWaiter());
        Order firstOrder = listOforders.get(0);
        int lastId = firstOrder.getWaiterId();
        int currentId;
        int i = 0;
        BigDecimal price = BigDecimal.valueOf(0);
        String ordersPriceAtAWaiter = "Celková cena všech objednávek pro jednotlivé číšníky za poslední " + h + " hodin(y): \n";
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
    }

    // vrátí String - průměrnou dobu zpracování obkednávek za posledních x hodin
    public String getAverageOfOrderTime(int h) throws RestaurantException {
        List<Order> listOfOrders = new ArrayList<>(getAllOrdersDuringLastHours(h));
        int totalProcesingMinutes = 0;
        int p = 0;
        for (Order order : listOfOrders) {
            if (order.getFulfilmentTime() != null) {
                Duration processingTime = Duration.between(order.getOrderedTime(), order.getFulfilmentTime());
                long processingMinutes = processingTime.toMinutes();
                totalProcesingMinutes += processingMinutes;
                p++;
            }
        }
        if (p == 0) {
            throw new RestaurantException("Nenalezeny žádné uzavřené objednávky");
        }
        int averageTime = (totalProcesingMinutes / p);
        String message = "Průměrná doba zpracování objednávek za poslední " + h + " hodin(y): " + averageTime + " minut(y) \n" +
                "Celkem uzavřených objednávek: " + p + "\n";
        return message;
    }

    // vrátí String - seznam všech jídel, které byly obejdnány za posledníx x hodin
    public String getAllOrderdDishes(int h) {
        List<Order> allOrdersL = new ArrayList<>(getAllOrdersAsList());
        Set<Dish> allDishesS = new HashSet<>();
        for (Order order : allOrdersL) {
            allDishesS.add(order.getDish());
        }
        List<Dish> allDishes = new ArrayList<>(allDishesS);
        Collections.sort(allDishes, new DishComparator());
        String allOrderedDishes = "Seznam všech objednaných jídel za poslední " + h + " hodin(y): \n";
        for (Dish dish : allDishes) {
            allOrderedDishes += dish.getTitle() + "\n";
            ;
        }
        return allOrderedDishes;
    }

    public String getAllOrdersFromOneOrderList(int a) throws RestaurantException {
        List<Order> orders = getAllOrdersAsList();
        int c = 0;
        int i;
        String s = "** Objednávky pro stůl č.";
        for (i = 0; i < orders.size() && c == 0; i++) {
            if (orders.get(i).getOrderList() == a) {
                c = orders.get(i).getTableId();
            }
        }
        if (c == 0) {
            throw new RestaurantException("Nepodařilo se najít žádné objednávky k id: " + a);
        }
        String d;
        if ((c >= 1) && (c <= 9)) {
            d = " " + String.valueOf(c);
        } else {
            d = String.valueOf(c);
        }
        s += d + " **\n" + "****\n";
        for (Order order : orders) {
            if (order.getOrderList() == a) {
                BigDecimal b = (order.getDish().getPrice()).multiply(BigDecimal.valueOf(order.getNumberOfPieces()));
                String orderStart = order.getOrderedTime().format(DateTimeFormatter.ofPattern("HH:mm"));
                String orderFinish;
                if (order.getFulfilmentTime() == null) {
                    orderFinish = "neuzavřeno";
                } else orderFinish = order.getFulfilmentTime().format(DateTimeFormatter.ofPattern("HH:mm"));
                s += order.getOrderId() + ". " + order.getDish().getTitle() + " " + order.getNumberOfPieces() + "x (" + b + "Kč):\t" + orderStart + "-" + orderFinish + "\tčíšník č. " + order.getWaiterId() + "\n";
            }
        }
        s += "******\n";
        return s;
    }

}


