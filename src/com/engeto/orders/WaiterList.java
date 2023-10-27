package com.engeto.orders;

import com.engeto.services.RestaurantException;
import com.engeto.services.RestaurantSettings;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class WaiterList {
    private static List<Waiter> waiters = new ArrayList<>();
    private static final File inputFile = new File(RestaurantSettings.getInputFileWaiters());

    public static List<Waiter> getWaiters() {
        return waiters;
    }

    public static void setWaiters(List<Waiter> waiters) {
        WaiterList.waiters = waiters;
    }

    public static File getInputFile() {
        return inputFile;
    }

    // přidá číšníka do listu
    public static void addWaiterToList(Waiter waiter) {
        waiters.add(waiter);
    }

    // vrátí číšníka z listu s daným id
    public static Waiter getWaiterFromList(int idNumber) throws RestaurantException {
        for (Waiter waiter : waiters) {
            if (waiter.getId() == idNumber) {
                return waiter;
            }
        }
        throw new RestaurantException("Nepodařilo se nalézt číšníka s uvedeným id: " + idNumber);
    }

    //vrátí seznam všech číšníků
    public static String getAllWaiters() {
        String allWaiters = "Seznam všech číšníků: \n";
        for (Waiter waiter : waiters) {
            allWaiters += waiter + "\n";
        }
        return allWaiters;
    }

    //načte seznam číšníků ze souboru
    public static void addAllFromFileToWaiterList(File fileName) throws RestaurantException {
        int lineNumber = 0;
        String[] items = new String[0];
        String line = "";
        if (fileName.exists()) {
            try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))) {
                while (scanner.hasNextLine()) {
                    lineNumber++;
                    line = scanner.nextLine();
                    items = line.split(RestaurantSettings.getItemsSeparator());
                    if (items.length == 3) {
                        Waiter waiter = new Waiter(Integer.parseInt(items[0]), items[1], items[2]);
                    } else {
                        throw new RestaurantException("Špatný počet položek na řádku " + lineNumber);
                    }
                }
            } catch (NumberFormatException e) {
                throw new RestaurantException("Špatnš zadaná hodnota id v souboru " + fileName + " na řádku " + lineNumber + ": " + items[0] + "\n" + e.getLocalizedMessage());
            } catch (FileNotFoundException e) {
                throw new RestaurantException("Nepodařilo se nalézt soubor " + fileName + "\n" + e.getLocalizedMessage());
            }
        }
    }

    //uloží seznam číšníků na disk
    public static void saveAllWaitersToFile(String FileName) throws RestaurantException {
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(FileName)))) {
            for (Waiter waiter : waiters) {
                String record = waiter.getId() + RestaurantSettings.getItemsSeparator()
                        + waiter.getName() + RestaurantSettings.getItemsSeparator()
                        + waiter.getSurname();
                printWriter.println(record);
            }
            System.out.println("Aktuální seznam číšníků byl uložen do souboru: " + FileName);
        } catch (IOException e) {
            throw new RestaurantException("Došlo k chybě při zápisu do souboru" + e.getLocalizedMessage());
        }
    }

    //vrátí aktuální id
    public static int checkNextid() {
        List<Integer> idS = new ArrayList<>();
        if (waiters.isEmpty()) {
            return 1;
        }
        for (Waiter waiter : waiters) {
            idS.add(waiter.getId());
        }
        return Collections.max(idS) + 1;
    }

    //vrátí list id
    public static List allIdS() {
        List<Integer> idS = new ArrayList<>();
        for (Waiter waiter : waiters) {
            idS.add(waiter.getId());
        }
        return idS;
    }

    //odebere číšníka s daným id ze seznamu
    public void removeWaiterWithId(int idNumber) throws RestaurantException {
        for (Waiter waiter : waiters) {
            if (waiter.getId() == idNumber) {
                waiters.remove(idNumber);
            } else throw new RestaurantException("Nepodařilo se najít číšníka s uvedeným id: " + idNumber);
        }
    }

    //odebere všechny číšníky ze seznamu
    public void removeAllWaitersFromList() {
        waiters.clear();
    }
}
