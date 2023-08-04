package com.engeto.services;

public class RestaurantSettings {
    private static final String INPUT_FILE_DISHREP = "dish_repertoire.txt";
    private static final String OUTPUT_FILE_DISHREP = "dish_repertoire.txt";
    private static final String INPUT_FILE_DISHMENU = "dish_menu.txt";
    private static final String OUTPUT_FILE_DISHMENU = "dish_menu.txt";
    private static final String INPUT_FILE_WAITERS = "waiters.txt";
    private static final String OUTPUT_FILE_WAITERS = "waiters.txt";
    private static final String INPUT_FILE_TABLES = "tables.txt";
    private static final String OUTPUT_FILE_TABLES = "tables.txt";
    private static final String INPUT_FILE_ORDERS = "orders.txt";
    private static final String OUTPUT_FILE_ORDERS = "orders.txt";
    private static final String ITEMS_SEPARATOR = "\t";
    private static final String IMAGES_SEPARATOR = ";";

    public static String getInputFileDishrep() {
        return INPUT_FILE_DISHREP;
    }

    public static String getOutputFileDishrep() {
        return OUTPUT_FILE_DISHREP;
    }

    public static String getInputFileDishmenu() {
        return INPUT_FILE_DISHMENU;
    }

    public static String getOutputFileDishmenu() {
        return OUTPUT_FILE_DISHMENU;
    }

    public static String getInputFileWaiters() {
        return INPUT_FILE_WAITERS;
    }

    public static String getOutputFileWaiters() {
        return OUTPUT_FILE_WAITERS;
    }

    public static String getInputFileTables() {
        return INPUT_FILE_TABLES;
    }

    public static String getOutputFileTables() {
        return OUTPUT_FILE_TABLES;
    }

    public static String getItemsSeparator() {
        return ITEMS_SEPARATOR;
    }

    public static String getImagesSeparator() {
        return IMAGES_SEPARATOR;
    }

    public static String getInputFileOrders() {
        return INPUT_FILE_ORDERS;
    }

    public static String getOutputFileOrders() {
        return OUTPUT_FILE_ORDERS;
    }

}
