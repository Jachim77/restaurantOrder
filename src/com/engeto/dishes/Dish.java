package com.engeto.dishes;

import com.engeto.services.RestaurantException;
import com.engeto.services.RestaurantSettings;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dish implements Comparable<Dish> {
    private String title;
    private BigDecimal price;
    private int preparationTime;
    private DishCategory dishCategory;
    private List<String> listOfImages = new ArrayList<>();

    public Dish(String title, BigDecimal price, int preparationTime, DishCategory dishCategory, String allImages) throws RestaurantException {
        try {
            this.title = title;
            this.price = price;
            this.preparationTime = preparationTime;
            this.dishCategory = dishCategory;
            listOfImages.addAll(List.of(allImages.split((RestaurantSettings.getImagesSeparator()))));
        } catch (NumberFormatException e) {
            throw new RestaurantException("Špatně zadané číslo:" + price + " ," + preparationTime + "\n" + e.getLocalizedMessage());
        }
    }

    public Dish(String title, BigDecimal price, int preparationTime, DishCategory dishCategory) throws RestaurantException {
        this(title, price, preparationTime, dishCategory, "blank");
    }

    //region
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public DishCategory getDishCategory() {
        return dishCategory;
    }

    public void setDishCategory(DishCategory dishCategory) {
        this.dishCategory = dishCategory;
    }

    public List<String> getListOfImages() {
        return listOfImages;
    }

    public void setListOfImages(List<String> listOfImages) throws RestaurantException {
        if (listOfImages == null) {
            throw new RestaurantException("Nebyly zadány žádné fotografie");
        }
        this.listOfImages = listOfImages;
    }
    //endregion

    // přidání fotografie
    public void addImage(String image) throws RestaurantException {
        if (image == null) {
            throw new RestaurantException("Fotografii nelze přidat, nebyl zadán žádný název");
        }
        listOfImages.add(image);
    }

    //odebrání fotografie
    public void removeImage(int number) throws RestaurantException {
        if (listOfImages.size() <= 1) {
            throw new RestaurantException("Fotografii nelze odebrat, v seznamu je pouze jedna fotografie");
        }
        listOfImages.remove(number);
    }


    @Override
    public String toString() {
        return "Dish{" +
                "title='" + title +
                ", price=" + price +
                ", preparationTime=" + preparationTime +
                ", dishCategory=" + dishCategory +
                ", listOfImages=" + listOfImages +
                '}';
    }

    @Override
    public int compareTo(Dish dish) {
        int result = getTitle().compareTo(dish.getTitle());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return preparationTime == dish.preparationTime && Objects.equals(title, dish.title) && Objects.equals(price, dish.price) && dishCategory == dish.dishCategory && Objects.equals(listOfImages, dish.listOfImages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, price, preparationTime, dishCategory, listOfImages);
    }
}
