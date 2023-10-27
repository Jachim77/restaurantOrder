package com.engeto.dishes;

import com.engeto.services.RestaurantException;
import com.engeto.services.RestaurantSettings;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class DishRepertoire {
    private List<Dish> dishRep = new ArrayList<>();

    public List<Dish> getDishRep() {
        return dishRep;
    }

    public void setDishRep(List<Dish> dishRep) {
        this.dishRep = dishRep;
    }

    public void addAllFromFileToRepertoire(String fileName) {
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
                        dishRep.add(dish);
                    } else if (items.length == 4) {
                        Dish dish = new Dish(items[0], new BigDecimal(items[1]), Integer.parseInt(items[2]), DishCategory.valueOf(items[3]));
                        dishRep.add(dish);
                    } else {
                        System.err.println("Špatný počet položek na řádku " + lineNumber + "\n jídlo nebylo ořidáno do repertáru");
                    }
                } catch (NumberFormatException | RestaurantException e) {
                    System.err.println("Špatně zadaná jedna nebo více hodnot v souboru " + fileName + " na řádku " + lineNumber + ": " + items[1] + "," + items[2] + "\n-- jídlo nebylo přidáno do repertoáru --" + "\n" + e.getLocalizedMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Nepodařilo se nalézt soubor " + fileName + "\n" + e.getLocalizedMessage());
        }
    }

    public void saveAllFromRepertoireToFile(String FileName) throws RestaurantException {
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(FileName)))) {
            for (Dish dish : dishRep) {
                String record = dish.getTitle() + RestaurantSettings.getItemsSeparator()
                        + dish.getPrice() + RestaurantSettings.getItemsSeparator()
                        + dish.getPreparationTime() + RestaurantSettings.getItemsSeparator()
                        + dish.getDishCategory() + RestaurantSettings.getItemsSeparator();
                int i = 1;
                List<String> listOfImages = new ArrayList<>(dish.getListOfImages());
                for (String image : listOfImages) {
                    record += image;
                    if (i < listOfImages.size()) {
                        record += RestaurantSettings.getImagesSeparator();
                    }
                    i++;
                }
                printWriter.println(record);
            }
            System.out.println("Aktuální seznam jídel byl uložen do souboru: " + FileName);
        } catch (IOException e) {
            throw new RestaurantException("Došlo k chybě při zápisu do souboru" + e.getLocalizedMessage());
        }
    }

    // vrátí řetězec - seznam všech jídel
    public String getDishRepertoire() {
        String allDishes = "Repertoár jídel: \n";
        int i = 0;
        for (Dish dish : dishRep) {
            allDishes += "Pozice:" + i + ", název: " + dish.getTitle() + ", cena: " + dish.getPrice() + ",-Kč, doba přípravy: " + dish.getPreparationTime() + "min, kategorie: " + dish.getDishCategory().getDescription() + ", fotografie: " + dish.getListOfImages() + "\n";
            i++;
        }
        return allDishes;
    }

    // přidání nového jídla do repertoáru
    public void addDishToDishRep(Dish dish) {
        dishRep.add(dish);
    }

    // odebrání jídla z repertoáru na určené pozici
    public void removeDishFromDishRep(int indexR) {
        if (dishRep.size() >= indexR + 1) {
            dishRep.remove(indexR);
        }
    }

    // vrátí jídlo na určené pozici
    public Dish getDishAtIndex(int index) {
        return dishRep.get(index);
    }

    //setřídí Arraylist podle jména
    public void sort() {
        Collections.sort(dishRep);
    }

    //setřídí Arraylist podle kategorie
    public void sort(Comparator<Dish> comparator) {
        Collections.sort(dishRep, comparator);
    }

}
