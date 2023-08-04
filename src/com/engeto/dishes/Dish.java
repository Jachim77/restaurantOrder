package com.engeto.orders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dish implements Comparable<Dish>{
    private String title;
    private BigDecimal price;
    private int preparationTime;
    private DishCategory dishCategory;
    private List<String> listOfImages = new ArrayList<>();

    public Dish (String title, BigDecimal price, int preparationTime,DishCategory dishCategory, String allImages) throws DishException {
        try {
            this.title = title;
            this.price = price;
            this.preparationTime = preparationTime;
            this.dishCategory = dishCategory;
            listOfImages.addAll(List.of(allImages.split((Settings.getImagesSeparator()))));
        }catch (NumberFormatException e) {
            throw new DishException("Špatně zadané číslo:"+ price+ " ," + preparationTime + "\n" + e.getLocalizedMessage());
        }
    }

    public Dish(String title, BigDecimal price, int preparationTime, DishCategory dishCategory) throws DishException{
        this(title,price,preparationTime,dishCategory,"blank");
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

    public void setListOfImages(List<String> listOfImages) throws DishException {
        if (listOfImages == null) {
            throw new DishException("Nebyly zadány žádné fotografie");
        }
        this.listOfImages = listOfImages;
    }
    //endregion

    // přidání fotografie
    public void addImage (String image) throws DishException{
        if (image==null) {
            throw new DishException("Fotografii nelze přidat, nebyl zadán žádný název");
        }
        listOfImages.add(image);
    }

    //odebrání fotografie
    public void removeImage (int c) throws DishException{
        if (listOfImages.size() <= 1) {
            throw new DishException("Fotografii nelze odebrat, v seznamu je pouze jedna fotografie");
        }
        listOfImages.remove(c);
    }


    @Override
    public String toString() {
        return  "Dish{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", preparationTime=" + preparationTime +
                ", dishCategory=" + dishCategory +
                ", listOfImages=" + listOfImages +
                '}' + "\n";
    }

    @Override
    public int compareTo (Dish o) {
        int result = getTitle().compareTo(o.getTitle());
        return result;
    }


}
