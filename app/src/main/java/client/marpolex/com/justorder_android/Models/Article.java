package client.marpolex.com.justorder_android.Models;

/**
 * Created by mario on 08/04/2018.
 */

public class Article {
    long id;
    String name;
    String description;
    float pvp;
    String img;
    float rating;

    public Article(long id, String name, String description, float pvp, String img, float rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pvp = pvp;
        this.img = img;
        this.rating = rating;
    }
}
