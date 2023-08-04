package com.engeto.orders;

import com.engeto.services.RestaurantException;
import com.engeto.services.RestaurantSettings;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TableList {
    private static List<Table> tables = new ArrayList<>();
    private static final File inputFile = new File(RestaurantSettings.getInputFileTables());

    public static List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public static File getInputFile() {
        return inputFile;
    }

    //přidá stůl do listu
    public static void addTableToList(Table table) {
        tables.add(table);
    }

    //vrátí seznam všech stolů
    public static String getAllTables() {
        String s = "Seznam všech stolů: \n";
        for (Table table : tables) {
            s += "Stůl číslo: " + table.getNumberOfTable() + ", počet míst: " + table.getNumberOfSeats() + "\n";
        }
        return s;
    }

    //vrátí stůl se zadaným id
    public static Table getTableFromList(int d) throws RestaurantException {
        for (Table table : tables) {
            if (table.getNumberOfTable() == d) {
                return table;
            }
        }
        throw new RestaurantException("Nepodařilo se najít stůl s uvedenýn id:" + d);
    }

    //odebere stúl s daným id ze seznamu
    public void removeTableWithId(int d) throws RestaurantException {
        for (Table table : tables) {
            if (table.getNumberOfTable() == d) {
                tables.remove(table);
            } else throw new RestaurantException("Nepodařilo se najít stůl s uvedeným id: " + d);
        }
    }

    //odebere všechny stoly ze seznamu
    public void removeAllTablesFromList() {
        tables.clear();
    }

    //načte seznam stolů ze souboru
    public static void addAllFromFileToTableList(File fileName) throws RestaurantException {
        int lineNumber = 0;
        String[] items = new String[0];
        String line = "";
        if (fileName.exists()) {
            try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))) {
                while (scanner.hasNextLine()) {
                    lineNumber++;
                    line = scanner.nextLine();
                    items = line.split(RestaurantSettings.getItemsSeparator());
                    if (items.length == 2) {
                        Table table = new Table(Integer.parseInt(items[0]), Integer.parseInt(items[1]));
                    } else {
                        throw new RestaurantException("Špatný počet položek na řádku " + lineNumber);
                    }
                }
            } catch (NumberFormatException e) {
                throw new RestaurantException("Špatně zadaná jedna nebo více hodnot v souboru " + fileName + " na řádku " + lineNumber + ": " + items[0] + "," + items[1] + "\n" + e.getLocalizedMessage());
            } catch (FileNotFoundException e) {
                throw new RestaurantException("Nepodařilo se nalézt soubor " + fileName + "\n" + e.getLocalizedMessage());
            }
        }
    }

    //uloží seznam stolů na disk
    public static void saveAllTablesToFile(String FileName) throws RestaurantException {
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(FileName)))) {
            for (Table table : tables) {
                String record = table.getNumberOfTable() + RestaurantSettings.getItemsSeparator()
                        + table.getNumberOfSeats();
                printWriter.println(record);
            }
        } catch (IOException e) {
            throw new RestaurantException("Došlo k chybě při zápisu do souboru" + e.getLocalizedMessage());
        }
    }

}
