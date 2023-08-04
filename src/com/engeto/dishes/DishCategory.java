package com.engeto.orders;

public enum DishCategory {
    SOUP("soup"),BEEF_MEAT("beef meat"),PORK_MEAT("pork meat"),POULTRY_MEAT("poultry meat"),VEGETARIAN_FOOD("vegetarian food"),DRINKS("drinks");

    private String description;

    DishCategory (String description){
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
