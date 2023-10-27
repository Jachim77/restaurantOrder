package com.engeto.dishes;

import com.engeto.services.RestaurantException;
import com.engeto.services.RestaurantSettings;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class DishMenu {
    private static List<Dish> menu = new ArrayList<>();
    private static final File file = new File(RestaurantSettings.getInputFileDishmenu());

    public static List<Dish> getMenu() {
        return menu;
    }

    public static File getFile() {
        return file;
    }

    // přidá jídlo z repertoáru do menu
    public static void addDishToMenu(DishRepertoire dishR, int indexR) {
        Set<Dish> menuS = new HashSet<>(menu);
        if (dishR.getDishRep().size() >= indexR + 1) {
            menuS.add(dishR.getDishAtIndex(indexR));
            menu = new ArrayList<>(menuS);
            Collections.sort(menu, new DishComparator());
        }
    }

    // odebere jídlo z menu
    public static void removeDishFromMenu(int index) {
        if (menu.size() >= index + 1) {
            menu.remove(index);
        }
    }

    // odebere všechna jídla z menu
    public static void removeAllDishesFromMenu() {
        menu.clear();
    }

    // vrátí seznam všech jídel v menu
    public static String getAllDishMenu() {
        String allDishesMenu = "Menu: \n";
        int i = 0;
        for (Dish dish : menu) {
            allDishesMenu += "Pozice:" + i + ", název: " + dish.getTitle() + ", cena: " + dish.getPrice() + ",-Kč, doba přípravy: " + dish.getPreparationTime() + "min, kategorie: " + dish.getDishCategory().getDescription() + ", fotografie: " + dish.getListOfImages() + "\n";
            i++;
        }
        return allDishesMenu;
    }

    public static void addAllFromFileToMenu(String fileName) {
        int lineNumber = 0;
        String[] items = new String[0];
        String line = "";
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))) {
            while (scanner.hasNextLine()) {
                try {
                    lineNumber++;
                    line = scanner.nextLine();
                    items = line.split(RestaurantSettings.getItemsSeparator());
                    if (items.length == 5) {
                        Dish dish = new Dish(items[0], new BigDecimal(items[1]), Integer.parseInt(items[2]), DishCategory.valueOf(items[3]), items[4]);
                        menu.add(dish);
                    } else if (items.length == 4) {
                        Dish dish = new Dish(items[0], new BigDecimal(items[1]), Integer.parseInt(items[2]), DishCategory.valueOf(items[3]));
                        menu.add(dish);
                    } else {
                        System.err.println("Špatný počet položek na řádku " + lineNumber + "\n jídlo nebylo ořidáno do menu");
                    }
                } catch (NumberFormatException | RestaurantException e) {
                    System.err.println("Špatně zadaná jedna nebo více hodnot v souboru " + fileName + " na řádku " + lineNumber + ": " + items[1] + "," + items[2] + "\n-- jídlo nebylo přidáno do menu --" + "\n" + e.getLocalizedMessage());
                }
                Collections.sort(menu, new DishComparator());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Nepodařilo se nalézt soubor " + fileName + "\n" + e.getLocalizedMessage());
        }
    }

    public static void saveAllFromMenuToFile(String FileName) throws RestaurantException {
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(FileName)))) {
            for (Dish dish : menu) {
                String record = dish.getTitle() + RestaurantSettings.getItemsSeparator()
                        + dish.getPrice() + RestaurantSettings.getItemsSeparator()
                        + dish.getPreparationTime() + RestaurantSettings.getItemsSeparator()
                        + dish.getDishCategory() + RestaurantSettings.getItemsSeparator();
                for (String image : dish.getListOfImages()) {
                    record = record + image + ";";
                }
                ;
                printWriter.println(record);
            }
            System.out.println("Aktuální menu bylo uloženo do souboru: " + FileName);
        } catch (IOException e) {
            throw new RestaurantException("Došlo k chybě při zápisu do souboru" + e.getLocalizedMessage());
        }
    }
}
