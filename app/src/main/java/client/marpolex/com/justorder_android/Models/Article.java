package client.marpolex.com.justorder_android.Models;

import java.io.Serializable;

/**
 * Created by mario on 08/04/2018.
 */

public class Article implements Serializable{
    long id;
    String name;
    String description;
    float pvp;
    String img;
    float rating;

    //Only functional variables for the shopping cart
    int quantity = 0;

    public Article(long id, String name, String description, float pvp, String img, float rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pvp = pvp;
        this.img = img;
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getPvp() {
        return pvp;
    }

    public String getImg() {
        return img;
    }

    public float getRating() {
        return rating;
    }
}
