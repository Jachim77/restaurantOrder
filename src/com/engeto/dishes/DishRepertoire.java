package com.engeto.orders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;

public class DishRepertoire {
    private List<Dish> dishRep = new ArrayList<>();

    public void addAllFromFile(String fileName) throws DishException {
        int lineNumber = 0;
        String[] items = new String[0];
        String line = "";
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))) {
            while (scanner.hasNextLine()) {
                lineNumber++;
                line = scanner.nextLine();
                items = line.split(Settings.getItemsSeparator());
                if (items.length == 5) {
                    Dish dish = new Dish(items[0], new BigDecimal(items[1]), Integer.parseInt(items[2]), DishCategory.valueOf(items[3]), items[4]);
                    dishRep.add(dish);
                } else if (items.length == 4) {
                    Dish dish = new Dish(items[0], new BigDecimal(items[1]), Integer.parseInt(items[2]), DishCategory.valueOf(items[3]));
                    dishRep.add(dish);
                } else {
                    throw new DishException("Špatný počet položek na řádku " + lineNumber);
                }
            }
        } catch (FileNotFoundException e) {
            throw new DishException("Nepodařilo se nalézt soubor " + fileName + "\n" + e.getLocalizedMessage());
        }
    }

    // vrátí seznam všech jídel
    public String getDishRepertoire() {
        String allDishes = "Repertoár jídel: \n";
        int a = 0;
        for (Dish dish : dishRep) {
            allDishes += "Pozice:"+ a + ", název: " + dish.getTitle() + ", cena: " + dish.getPrice() + ",-Kč, doba přípravy: " + dish.getPreparationTime() + "min, kategorie: " + dish.getDishCategory() + ", fotografie: " + dish.getListOfImages() + "\n";
            a++;
        }
        return allDishes;
    }

    // přidání nového jídla do repertoáru
    public void addDishToDishRep(Dish dish) {
        dishRep.add(dish);
    }

    // odebrání jídla z repertoáru na určené pozici
    public void removeDishFromDishRep(int b) {dishRep.remove(b);
    }

    // vrátí jídlo na určené pozici
    public Dish getDishAtIndex (int index) {
        return dishRep.get(index);
    }


    //setřídí Arraylist podle jména
    public void sort() {
        Collections.sort(dishRep);
    }

    //setřídí Arraylist podle kategorie
    public void sort (Comparator<Dish> comparator) {
        Collections.sort(dishRep,comparator);
    }

}
