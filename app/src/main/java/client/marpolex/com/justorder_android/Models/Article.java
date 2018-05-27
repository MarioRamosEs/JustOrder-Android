package client.marpolex.com.justorder_android.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Article implements Serializable {
    private long id;
    private int recyclerId;
    private String name;
    private String description;
    private String ref_code;
    private double base_price;
    private String tax_type;
    private String image;
    private String product_type;
    private String product_subtype;
    private String created_at;
    private String updated_at;
    private double rating;
    private ArrayList<Rating> ratingsList;

    public Article(JSONObject jsonObject, int recyclerId) {
        ratingsList = new ArrayList<>();
        this.recyclerId = recyclerId;
        try {
            id = jsonObject.getInt("id");
            name = jsonObject.getString("name");
            ref_code = jsonObject.getString("ref_code");
            base_price = jsonObject.getDouble("base_price");
            tax_type = jsonObject.getString("tax_type");
            product_type = jsonObject.getString("product_type");
            product_subtype = jsonObject.getString("product_subtype");
            //created_at = jsonObject.getString("created_at");
            //updated_at = jsonObject.getString("updated_at");

            if (jsonObject.isNull("description")) description = "";
            else description = jsonObject.getString("description");

            if (jsonObject.isNull("image")) image = "";
            else image = jsonObject.getString("image");

            if (jsonObject.has("rating")) {
                if(!jsonObject.isNull("rating"))
                    rating = jsonObject.getDouble("rating");
                else rating = 0;
            } else rating = 0;

            //Ratings
            JSONArray jsonRatings = jsonObject.getJSONArray("ratings");
            for (int i = 0; i < jsonRatings.length(); i++) {
                ratingsList.add(new Rating(jsonRatings.getJSONObject(i), i));
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

    public String getDescription() {
        return description;
    }

    public double getBase_price() {
        return base_price;
    }

    public String getImage() {
        return image;
    }

    public double getRating() {
        return rating;
    }

    public int getRecyclerId() {
        return recyclerId;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ref_code='" + ref_code + '\'' +
                ", base_price=" + base_price +
                ", tax_type='" + tax_type + '\'' +
                ", image='" + image + '\'' +
                ", product_type='" + product_type + '\'' +
                ", product_subtype='" + product_subtype + '\'' +
                // ", created_at='" + created_at + '\'' +
                // ", updated_at='" + updated_at + '\'' +
                ", rating=" + rating +
                '}';
    }
}
