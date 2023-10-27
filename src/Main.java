import com.engeto.dishes.*;
import com.engeto.managers.RestaurantManager;
import com.engeto.orders.*;
import com.engeto.services.RestaurantException;
import com.engeto.services.RestaurantSettings;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        try {
            //Úloha 1- načtení stavu evidence z disku - repertoár jídel, menu, seznam číšníků, stolů, objednávek
            DishRepertoire dishRepertoire = new DishRepertoire();
            dishRepertoire.addAllFromFileToRepertoire(RestaurantSettings.getInputFileDishrep());
            System.out.println("-- Úloha 1 - načtený repertoár jídel ze souboru --");
            System.out.println(dishRepertoire.getDishRepertoire());

            System.out.println("-- Úloha 1 - načtené menu ze souboru --");
            DishMenu.addAllFromFileToMenu(RestaurantSettings.getInputFileDishmenu());
            System.out.println("Načtené menu ze souboru:");
            System.out.println(DishMenu.getAllDishMenu());

            System.out.println("-- Úloha 1 - načtený seznam číšníků ze souboru --");
            WaiterList.addAllFromFileToWaiterList(WaiterList.getInputFile());
            System.out.println(WaiterList.getAllWaiters());

            System.out.println("-- Úloha 1 - načtený seznam stolů ze souboru --");
            TableList.addAllFromFileToTableList(TableList.getInputFile());
            System.out.println(TableList.getAllTables());

            System.out.println("-- Úloha 1 - načtený seznam všech objednávek ze souboru --");
            OrderList.addAllOrdersFromFile(RestaurantSettings.getInputFileOrders());
            System.out.println(OrderList.getAllOrders());

            //Úloha 2 - vytvořena nová jídla a přidána do repertoáru
            Dish dish1 = new Dish("kuřecí řízek obalovaný 150g", BigDecimal.valueOf(140), 20, DishCategory.POULTRY_MEAT, "kureci_rizek_01;kureci_rizek_02");
            Dish dish2 = new Dish("hranolky 150g", BigDecimal.valueOf(80), 10, DishCategory.VEGETARIAN_FOOD, "hranolky_01;hranolky_02");
            Dish dish3 = new Dish("pstruh na víně 200g", BigDecimal.valueOf(210), 20, DishCategory.FISH, "pstruh_na_vine_01;pstruh_na_vine_02");
            Dish dish4 = new Dish("kofola", BigDecimal.valueOf(35), 3, DishCategory.DRINKS, "kofola_01");
            dishRepertoire.addDishToDishRep(dish1);
            dishRepertoire.addDishToDishRep(dish2);
            dishRepertoire.addDishToDishRep(dish3);
            dishRepertoire.addDishToDishRep(dish4);
            System.out.println("-- Úloha 2 - vytvořena nová jídla a přidána do repertoáru --");
            System.out.println(dishRepertoire.getDishRepertoire());

            //Üloha 2 - první, druhé a čtvrté jídlo zařazeno do menu
            DishMenu.addDishToMenu(dishRepertoire, 18);
            DishMenu.addDishToMenu(dishRepertoire, 19);
            DishMenu.addDishToMenu(dishRepertoire, 21);
            System.out.println("-- Úloha 2 - zařazena nová jídla do menu --");
            System.out.println(DishMenu.getAllDishMenu());

            //Üloha 2 - nové objednávky pro stůl č.15
            System.out.println("-- Úloha 2 - nové objednávky pro stůl č.15 --");
            OrderList orderList5 = new OrderList(15);
            orderList5.addOrderToOrderList(5, 2, 1);
            orderList5.addOrderToOrderList(6, 2, 1);
            orderList5.addOrderToOrderList(7, 2, 1);
            try {
                orderList5.getOrderFromList(6).setFulfilmentTime(LocalDateTime.now());
            } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
                System.err.println(e.getLocalizedMessage());
            }
            orderList5.setComment("Hostům se líbila výzdoba restaurace");
            System.out.println();
            System.out.println(orderList5.getAllOrderToATable());

            //Úloha 2 - vytvořeny objednávky pro stůl č.2
            System.out.println("-- Úloha 2 - vytvořeny objednávky pro stůl č.2 --");
            OrderList orderList2 = new OrderList(2);
            orderList2.addOrderToOrderList(0, 1, 2);
            orderList2.addOrderToOrderList(3, 1, 2);
            orderList2.addOrderToOrderList(5, 1, 2);
            orderList2.addOrderToOrderList(7, 1, 3);
            orderList2.addOrderToOrderList(4, 1, 3);
            System.out.println();
            System.out.println(orderList2.getAllOrderToATable());

            //Úloha 3 - přidána objednávka jíddla, které není v menu
            System.out.println("-- Úloha 3 - přidána objednávka jíddla, které není v menu --");
            orderList2.addOrderToOrderList(15, 1, 1);
            System.out.println();

            //Úloha 4 - celková cena objednávek pro stůl č.15
            System.out.println("-- Úloha 4 - celková cena objednávek pro stůl č.15 --");
            System.out.println("Celková cena objednávek pro stůl č.15: " + orderList5.getToTalPriceForOrdersToATable() + ",- Kč");
            System.out.println();

            //Úloha 5 - informace pro management
            System.out.println("-- Úloha 5 - informace pro management --");
            RestaurantManager restaurantManager = new RestaurantManager();
            //vypíše seznam všech objednávek
            System.out.println(restaurantManager.getAllOrders());
            //vypíše seznam všech rozpracovaných objednávek
            System.out.println(restaurantManager.getAllOpenOrders());
            //setřídí rozpracované objednávky dle číšníka a potom dle data zadání
            System.out.println(restaurantManager.getOpenOrdresSortedbyWaiter());
            System.out.println(restaurantManager.getOpenOrdersSortedByOrderedTime());
            //celková cena všech objednávek dle číšníka za poslední 2 hodiny
            System.out.println(restaurantManager.getTotalOrdersPriceForEachWaiter(2));

            //průměrná doba zpracování objednávek za poslední 2 hodiny
            System.out.println(restaurantManager.getAverageOfOrderTime(2));

            //seznam všech objednaných jídel za poslední 2 hodiny
            System.out.println(restaurantManager.getAllOrderdDishes(2));

            //export seznamu objednávek pro jeden stůl
            System.out.println(restaurantManager.getAllOrdersFromOneOrderList(4));

            //Úloha 5 - uloží aktualizovaná data na disk
            System.out.println("-- Úloha 5 - uložení aktualizovaných dat na disk --");
            dishRepertoire.saveAllFromRepertoireToFile(RestaurantSettings.getOutputFileDishrep());
            DishMenu.saveAllFromMenuToFile(RestaurantSettings.getOutputFileDishmenu());
            OrderList.saveAllFromOrderSummaryToFile(RestaurantSettings.getOutputFileOrders());
            WaiterList.saveAllWaitersToFile(RestaurantSettings.getOutputFileWaiters());
            TableList.saveAllTablesToFile(RestaurantSettings.getOutputFileTables());

        } catch (RestaurantException e) {
            System.err.println(e.getLocalizedMessage());
        }

    }

}

