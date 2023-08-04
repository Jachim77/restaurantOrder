package com.engeto.dishes;

public class Settings {
    private static final String INPUT_FILE_DISHREP = "dish_repertoire.txt";
    private static final String OUTPUT_FILE_DISHREP = "dish_repertoire_update.txt";
    private static final String INPUT_FILE_DISHMENU = "dish_menu_update.txt";
    private static final String OUTPUT_FILE_DISHMENU = "dish_menu_update.txt";
    private static final String ITEMS_SEPARATOR = "\t";
    private static final String IMAGES_SEPARATOR = ";";

    public static String getInputFileDishrep() {return INPUT_FILE_DISHREP;}
    public static String getOutputFileDishrep() {return OUTPUT_FILE_DISHREP;}
    public static String getInputFileDishmenu() {return INPUT_FILE_DISHMENU;}
    public static String getOutputFileDishmenu() {return OUTPUT_FILE_DISHMENU;}

    public static String getItemsSeparator() {return ITEMS_SEPARATOR;}
    public static String getImagesSeparator() {return IMAGES_SEPARATOR;}
}
