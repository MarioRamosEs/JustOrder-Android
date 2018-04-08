package client.marpolex.com.justorder_android.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mario on 08/04/2018.
 */

public class Subcategory {
    long id;
    String name;
    List<Article> articleList = new ArrayList<>();

    public Subcategory(long id, String name, List<Article> articleList) {
        this.id = id;
        this.name = name;
        this.articleList = articleList;
    }
}
