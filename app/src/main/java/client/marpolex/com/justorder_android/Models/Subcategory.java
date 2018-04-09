package client.marpolex.com.justorder_android.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mario on 08/04/2018.
 */

public class Subcategory implements Serializable {
    long id;
    String name;
    List<Article> articleList = new ArrayList<>();

    public Subcategory(long id, String name, List<Article> articleList) {
        this.id = id;
        this.name = name;
        this.articleList = articleList;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Article> getArticleList() {
        return articleList;
    }
}
