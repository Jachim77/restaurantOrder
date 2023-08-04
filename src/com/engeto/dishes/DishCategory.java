package com.engeto.dishes;

public enum DishCategory {
    SOUP("soup"), BEEF_MEAT("beef meat"), PORK_MEAT("pork meat"), POULTRY_MEAT("poultry meat"), FISH("fish"), VEGETARIAN_FOOD("vegetarian food"), DRINKS("drinks");

    private String description;

    DishCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
