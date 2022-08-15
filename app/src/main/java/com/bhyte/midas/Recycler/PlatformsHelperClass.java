package com.bhyte.midas.Recycler;

public class PlatformsHelperClass {

    int image;
    String name, category;

    public PlatformsHelperClass(int image, String name, String category) {
        this.image = image;
        this.name = name;
        this.category = category;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getCategory() { return category; }
}
