package client.marpolex.com.justorder_android.Models;

import android.nfc.Tag;
import android.util.Log;

import com.orm.SugarRecord;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mario on 28/03/2018.
 */

public class Restaurant extends SugarRecord {

    String name;
    String direction;
    String openingHours;
    String imgUrl;
    float rating;
    //int idRestaurant;

    public Restaurant(JSONObject jsonObject) {
        try {
            //idRestaurant = jsonObject.getInt("id");
            name = jsonObject.getString("name");
            imgUrl = jsonObject.getString("imgUrl");
            direction = jsonObject.getString("direction");
            openingHours = jsonObject.getString("openingHours");
            rating = (float) jsonObject.getDouble("rating");
            //Log.d("Restaurante", "Restaurante "+name+" añadido.");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Restaurant(String name, String direction, String openingHours, String imgUrl, float rating) {
        this.name = name;
        this.direction = direction;
        this.openingHours = openingHours;
        this.imgUrl = imgUrl;
        this.rating = rating;
    }

    public Restaurant(){

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
}
