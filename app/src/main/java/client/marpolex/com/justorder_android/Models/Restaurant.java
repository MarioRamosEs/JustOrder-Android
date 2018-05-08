package client.marpolex.com.justorder_android.Models;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    private List<Rating> ratings = new ArrayList<>();

    public Restaurant(JSONObject jsonObject) {
        try {
            idRestaurant = jsonObject.getInt("id");
            name = jsonObject.getString("name");
            imgUrl = jsonObject.getString("imgUrl");
            logo = jsonObject.getString("logo");
            direction = jsonObject.getString("direction");
            openingHours = jsonObject.getString("schedule"); //Divergencia en nombre
            rating = (float) jsonObject.getDouble("rating");

            JSONArray jsonRatings = jsonObject.getJSONArray("ratings");
            for (int i = 0; i < jsonRatings.length(); i++) {
                ratings.add(new Rating(jsonRatings.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Restaurant(String name, String direction, String openingHours, String imgUrl, float rating, String logo, List<Rating> ratings) {
        this.name = name;
        this.direction = direction;
        this.openingHours = openingHours;
        this.imgUrl = imgUrl;
        this.rating = rating;
        this.logo = logo;
        this.ratings = ratings;
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

    public String getLogo() {
        return logo;
    }

    public float getRating() {
        return rating;
    }

    public int getIdRestaurant() {
        return idRestaurant;
    }

    public List<Rating> getRatings() {
        return ratings;
    }
}
