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
            //Úloha 1- načtení stavu evidence z disku
            DishRepertoire dishRepertoire = new DishRepertoire();
            dishRepertoire.addAllFromFileToRepertoire(DishRepertoire.getFile());
            System.out.println("-- Úloha 1 - načtený repertoár jídel ze souboru --");
            System.out.println(dishRepertoire.getDishRepertoire());

            System.out.println("-- Úloha 1 - načtené menu ze souboru --");
            DishMenu.addAllFromFileToMenu(DishMenu.getFile());
            System.out.println("Načtené menu ze souboru:");
            System.out.println(DishMenu.getAllDishMenu());

            System.out.println("-- Úloha 1 - načtený seznam číšníků ze souboru --");
            WaiterList.addAllFromFileToWaiterList(WaiterList.getInputFile());
            System.out.println(WaiterList.getAllWaiters());

            System.out.println("-- Úloha 1 - načtený seznam stolů ze souboru --");
            TableList.addAllFromFileToTableList(TableList.getInputFile());
            System.out.println(TableList.getAllTables());

            System.out.println("-- Úloha 1 - načtený seznam všech objednávek ze souboru --");
            OrderList.addAllOrdersFromFile(OrderList.getFile());
            System.out.println(OrderList.getAllOrders());

            //Úloha 2 - vytvořena nová jídla a přidána do repertoáru
            Dish dish1 = new Dish("kuřecí řízek obalovaný 150g", BigDecimal.valueOf(140), 20, DishCategory.POULTRY_MEAT, "kureci_rizek_01;kureci_rizek_02");
            Dish dish2 = new Dish("hranolky 150g", BigDecimal.valueOf(80), 10, DishCategory.VEGETARIAN_FOOD, "hranolky_01;hranolky_02");
            Dish dish3 = new Dish("pstruh na víně 200g", BigDecimal.valueOf(210), 20, DishCategory.FISH, "pstruh_na_vine_01;pstruh_na_vine_02");
            dishRepertoire.addDishToDishRep(dish1);
            dishRepertoire.addDishToDishRep(dish2);
            dishRepertoire.addDishToDishRep(dish3);
            System.out.println("-- Úloha 2 - vytvořena nová jídla a přidána do repertoáru --");
            System.out.println(dishRepertoire.getDishRepertoire());

            dishRepertoire.sort();
            System.out.println("--repertoár jídel setříděn podle názvu--");
            System.out.println(dishRepertoire.getDishRepertoire());

            dishRepertoire.sort(new DishComparator());
            System.out.println("--repertoár jídel setříděn podle kategorie--");
            System.out.println(dishRepertoire.getDishRepertoire());

            dishRepertoire.removeDishFromDishRep(15);
            System.out.println("--odebráno jídlo na pozici 15 z pův. repertoáru--");
            System.out.println(dishRepertoire.getDishRepertoire());

            //Úprava názvu, ceny a doby přípravy u jídla v repertoáru
            dishRepertoire.getDishAtIndex(0).setTitle("cibulačka se sýrem");
            dishRepertoire.getDishAtIndex(0).setPrice(BigDecimal.valueOf(65));
            dishRepertoire.getDishAtIndex(0).setPreparationTime(8);
            System.out.println("--Úprava názvu, ceny a doby přípravy u jídla v repertoáru na pozici 0--");
            System.out.println(dishRepertoire.getDishRepertoire());

            System.out.println("--Seznam Fotografií u jídla na poz.0--");
            System.out.println(dishRepertoire.getDishAtIndex(0).getListOfImages() + "\n");

            //Úprava seznamu fotografií u jídel v repertoáru
            dishRepertoire.getDishAtIndex(0).addImage("cibulacka_02");
            dishRepertoire.getDishAtIndex(2).removeImage(1);
            System.out.println("--přidána fotografie v repertoáru u jídla na pozici 0, odebrána fotografie u jídla na pozici 2 -- ");
            System.out.println(dishRepertoire.getDishRepertoire());


            //Úloha 2 - Kuřecí řízek a pstruh na víně přidány do menu
            DishMenu.addDishToMenu(dishRepertoire, 10);
            DishMenu.addDishToMenu(dishRepertoire, 12);
            System.out.println("-- Úloha 2 - manuálně přidána další jídla do menu - kuřecí řízek a pstruh na víně --");
            System.out.println(DishMenu.getAllDishMenu());

            //manuálně přidána další jídla do menu
            DishMenu.addDishToMenu(dishRepertoire, 11);
            DishMenu.addDishToMenu(dishRepertoire, 15);
            DishMenu.addDishToMenu(dishRepertoire, 4);
            DishMenu.addDishToMenu(dishRepertoire, 5);
            DishMenu.addDishToMenu(dishRepertoire, 16);
            System.out.println("-- manuálně přidána další jídla do menu --");
            System.out.println(DishMenu.getAllDishMenu());

            //vytvoření nového číšníka
            Waiter waiter = new Waiter("Dalibor", "Vrána");
            System.out.println("-- Přidán nový číšník do seznamu --");
            System.out.println(WaiterList.getAllWaiters());

            //vytvoření nového stolu, přidání do seznamu
            Table table4 = new Table(15, 10);
            System.out.println("-- Přidán nový stůl do seznamu --");
            System.out.println(TableList.getAllTables());

            //vytvoření Listu objednávek a přidání objednávek do Listu
            System.out.println("--Vytvoření nových objednávek k jednotlivým stolům--");
            OrderList orderList1 = new OrderList(1);
            orderList1.addOrderToOrderList(2, 3, 1);
            TimeUnit.SECONDS.sleep(1);
            orderList1.addOrderToOrderList(3, 1, 1);
            TimeUnit.SECONDS.sleep(1);
            orderList1.addOrderToOrderList(4, 2, 1);
            TimeUnit.SECONDS.sleep(1);
            orderList1.addOrderToOrderList(7, 1, 1);
            TimeUnit.SECONDS.sleep(1);
            orderList1.addOrderToOrderList(8, 1, 2);
            TimeUnit.SECONDS.sleep(1);
            System.out.println(orderList1.getAllOrderToATable());

            //Úloha 2 - vytvořeny objednávky pro stůl č.2
            OrderList orderList2 = new OrderList(2);
            orderList2.addOrderToOrderList(0, 1, 2);
            TimeUnit.SECONDS.sleep(1);
            orderList2.addOrderToOrderList(3, 1, 2);
            TimeUnit.SECONDS.sleep(1);
            orderList2.addOrderToOrderList(5, 1, 2);
            TimeUnit.SECONDS.sleep(1);
            orderList2.addOrderToOrderList(7, 1, 2);
            TimeUnit.SECONDS.sleep(1);
            orderList2.addOrderToOrderList(8, 1, 1);
            TimeUnit.SECONDS.sleep(1);
            System.out.println("-- Úloha 2 - vytvořeny objednávky pro stůl č.2 --");
            System.out.println(orderList2.getAllOrderToATable());

            OrderList orderList3 = new OrderList(3);
            orderList3.addOrderToOrderList(0, 3, 3);
            TimeUnit.SECONDS.sleep(1);
            orderList3.addOrderToOrderList(4, 2, 3);
            TimeUnit.SECONDS.sleep(1);
            orderList3.addOrderToOrderList(1, 3, 3);
            TimeUnit.SECONDS.sleep(1);
            orderList3.addOrderToOrderList(6, 3, 3);
            TimeUnit.SECONDS.sleep(1);
            orderList3.addOrderToOrderList(7, 1, 3);
            TimeUnit.SECONDS.sleep(1);
            orderList3.addOrderToOrderList(8, 1, 3);
            TimeUnit.SECONDS.sleep(1);
            orderList3.addOrderToOrderList(7, 1, 2);
            System.out.println(orderList3.getAllOrderToATable());

            //Úloha 2 - vytvořeny 3 objednávky pro stůl č.15
            OrderList orderList4 = new OrderList(15);
            orderList4.addOrderToOrderList(5, 2, 1);
            orderList4.addOrderToOrderList(6, 1, 2);
            orderList4.addOrderToOrderList(9, 2, 4);
            System.out.println("-- Úloha 2 - vytvořeny 3 objednávky pro stůl č.15 --");
            System.out.println(orderList4.getAllOrderToATable());

            //Úloha 2 - uzavření některých objednávek u stolu 2 a 15
            orderList2.getOrderFromList(9).setFulfilmentTime(LocalDateTime.now());
            orderList2.getOrderFromList(13).setFulfilmentTime(LocalDateTime.now());
            orderList4.getOrderFromList(23).setFulfilmentTime(LocalDateTime.now());
            System.out.println("-- Üloha 2 - uzavření některých objednávek u stolu 2 a 15 --");
            System.out.println(orderList2.getAllOrderToATable());
            System.out.println(orderList4.getAllOrderToATable());

            //Úloha 3 - přidána objednávka jíddla, které není v menu (vyhodí výjimku, proto je to zakomentováno)
            //orderList2.addOrderToOrderList(10,1,1);

            //Úloha 4 - uzavření některých dalších objednávek (fiktivním časem)
            System.out.println("--Úloha 4 - uzavření dalších objednávek-- \n");
            orderList1.getOrderFromList(4).setFulfilmentTime(LocalDateTime.now().plusMinutes(5));
            orderList1.getOrderFromList(5).setFulfilmentTime(LocalDateTime.now().plusMinutes(8));
            orderList1.getOrderFromList(7).setFulfilmentTime(LocalDateTime.now().plusMinutes(7));
            orderList2.getOrderFromList(11).setFulfilmentTime(LocalDateTime.now().plusMinutes(9));
            orderList2.getOrderFromList(10).setFulfilmentTime(LocalDateTime.now().plusMinutes(6));
            orderList3.getOrderFromList(15).setFulfilmentTime(LocalDateTime.now().plusMinutes(8));
            orderList3.getOrderFromList(16).setFulfilmentTime(LocalDateTime.now().plusMinutes(10));
            orderList3.getOrderFromList(17).setFulfilmentTime(LocalDateTime.now().plusMinutes(12));
            orderList3.getOrderFromList(18).setFulfilmentTime(LocalDateTime.now().plusMinutes(8));
            orderList4.getOrderFromList(21).setFulfilmentTime(LocalDateTime.now().plusMinutes(9));
            System.out.println(orderList1.getAllOrderToATable());
            System.out.println(orderList2.getAllOrderToATable());
            System.out.println(orderList3.getAllOrderToATable());
            System.out.println(orderList4.getAllOrderToATable());

            //Úloha 5 - informace pro management
            System.out.println("-- Úloha 5 - informace pro management --");
            RestaurantManager restaurantManager = new RestaurantManager();
            //vypíše seznam všech objednávek
            System.out.println(restaurantManager.getAllOrders());
            //vypíše seznam všech otevřených objednávek
            System.out.println(restaurantManager.getAllOpenOrders());
            //setřídí otevřené objednávky dle číšníka a potom dle data zadání
            System.out.println(restaurantManager.getOpenOrdresSortedbyWaiter());
            System.out.println(restaurantManager.getOpenOrdersSortedByOrderčedTime());
            //celková cena všech objednávek dle číšníka za poslední 2 hodiny
            System.out.println(restaurantManager.getTotalOrdersPriceForEachWaiter(2));

            //průměrná doba zpracování objednávek za poslední 2 hodiny
            System.out.println(restaurantManager.getAverageOfOrderTime(2));

            //seznam všech objednaných jídel za poslední 2 hodiny
            System.out.println(restaurantManager.getAllOrderdDishes(2));

            //export seznamu objednávek pro jeden stůl
            System.out.println(restaurantManager.getAllOrdersFromOneOrderList(4));

            //Úloha 5 - uloží aktualizovaná data na disk
            dishRepertoire.saveAllFromRepertoireToFile(RestaurantSettings.getOutputFileDishrep());
            DishMenu.saveAllFromMenuToFile(RestaurantSettings.getOutputFileDishmenu());
            OrderList.saveAllFromOrderSummaryToFile(RestaurantSettings.getOutputFileOrders());
            WaiterList.saveAllWaitersToFile(RestaurantSettings.getOutputFileWaiters());
            TableList.saveAllTablesToFile(RestaurantSettings.getOutputFileTables());

        } catch (RestaurantException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}