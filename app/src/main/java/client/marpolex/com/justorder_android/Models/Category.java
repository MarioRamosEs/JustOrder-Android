package client.marpolex.com.justorder_android.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mario on 08/04/2018.
 */

public class Category {
    long id;
    String name;
    String img;
    List<Subcategory> subcategories = new ArrayList<>();

    public Category(long id, String name, String img, List<Subcategory> subcategories) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.subcategories = subcategories;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public List<Subcategory> getSubcategories() {
        return subcategories;
    }
}
