package client.marpolex.com.justorder_android.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mario on 08/04/2018.
 */

public class Subcategory implements Serializable {
    String name;
    List<Article> articleList = new ArrayList<>();
    long id;

    public Subcategory(JSONObject jsonObject, long id) {
        this.id = id;
        try {
            name = jsonObject.getString("name");
            JSONArray articles = jsonObject.getJSONArray("articles");

            for (int i = 0; i < articles.length(); i++) {
                articleList.add(new Article(articles.getJSONObject(i), i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public long getId() {
        return id;
    }
}
