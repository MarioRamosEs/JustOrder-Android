package client.marpolex.com.justorder_android.Models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by mario on 08/04/2018.
 */

public class Article implements Serializable {
    private long id;
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

    public Article(JSONObject jsonObject) {
        try {
            id = jsonObject.getInt("id");
            name = jsonObject.getString("name");
            description = jsonObject.getString("description");
            ref_code = jsonObject.getString("ref_code");
            base_price = jsonObject.getDouble("base_price");
            tax_type = jsonObject.getString("tax_type");
            image = jsonObject.getString("image");
            product_type = jsonObject.getString("product_type");
            product_subtype = jsonObject.getString("product_subtype");
            created_at = jsonObject.getString("created_at");
            updated_at = jsonObject.getString("updated_at");
            rating = jsonObject.getDouble("rating");
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

    public String getRef_code() {
        return ref_code;
    }

    public double getBase_price() {
        return base_price;
    }

    public String getTax_type() {
        return tax_type;
    }

    public String getImage() {
        return image;
    }

    public String getProduct_type() {
        return product_type;
    }

    public String getProduct_subtype() {
        return product_subtype;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public double getRating() {
        return rating;
    }
}
