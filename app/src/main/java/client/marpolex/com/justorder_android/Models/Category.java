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

public class Category implements Serializable {
    int id;
    String name;
    String img;
    List<Subcategory> subcategoryList = new ArrayList<>();

    public Category(JSONObject jsonObject) {
        try {
            id = jsonObject.getInt("id");
            name = jsonObject.getString("name");
            img = jsonObject.getString("image");
            JSONArray subCategories = jsonObject.getJSONArray("subCategories");

            for (int i = 0; i < subCategories.length(); i++) {
                subcategoryList.add(new Subcategory(subCategories.getJSONObject(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        return subcategoryList;
    }
}
