package client.marpolex.com.justorder_android.Models;

import com.orm.SugarRecord;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mario on 28/03/2018.
 */

public class Restaurant extends SugarRecord {

    private String name;
    private String direction;
    private String openingHours;
    private String imgUrl;
    private String logo;
    private float rating;
    private int idRestaurant;

    public Restaurant(JSONObject jsonObject) {
        try {
            idRestaurant = jsonObject.getInt("id");
            name = jsonObject.getString("name");
            imgUrl = jsonObject.getString("imgUrl");
            logo = jsonObject.getString("logo");
            direction = jsonObject.getString("direction");
            openingHours = jsonObject.getString("openingHours");
            rating = (float) jsonObject.getDouble("rating");
            //Log.d("Restaurante", "Restaurante "+name+" añadido.");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Restaurant(String name, String direction, String openingHours, String imgUrl, float rating, String logo) {
        this.name = name;
        this.direction = direction;
        this.openingHours = openingHours;
        this.imgUrl = imgUrl;
        this.rating = rating;
        this.logo = logo;
    }

    public Restaurant() {

    }

    public String getName() {
        return name;
    }

    public String getDirection() {
        return direction;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public float getRating() {
        return rating;
    }

    public int getIdRestaurant() {
        return idRestaurant;
    }

    public String getLogo() {
        return logo;
    }
}
