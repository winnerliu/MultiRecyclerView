package com.kad.mrv.stagger.bean;

import java.io.Serializable;

/**
 * Created by Winner on 2016/5/13.
 */
public class TypeData implements Serializable {
    private String imageUrl;
    private String name;
    private String price;
    private String goalUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGoalUrl() {
        return goalUrl;
    }

    public void setGoalUrl(String goalUrl) {
        this.goalUrl = goalUrl;
    }

    @Override
    public String toString() {
        return "TypeData{" +
                "imageUrl='" + imageUrl + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", goalUrl='" + goalUrl + '\'' +
                '}';
    }
}
