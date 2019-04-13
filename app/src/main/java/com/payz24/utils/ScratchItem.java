package com.payz24.utils;

/**
 * Created by someo on 11-06-2018.
 */

public class ScratchItem {

    private  String price;
    private String text;

    public ScratchItem(String price) {
        this.price = price;

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
