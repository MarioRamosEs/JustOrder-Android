package client.marpolex.com.justorder_android.Models;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Rating implements Serializable{
    long id;
    private int idRating;
    private double rating;
    private String comment;
    private String createdAt;
    private String name;
    private String surnames;

    Rating(JSONObject jsonObject, long id){
        this.id = id;
        try {
            idRating = jsonObject.getInt("id");
            rating = jsonObject.getDouble("rating");
            createdAt = jsonObject.getString("created_at");

            JSONObject user = jsonObject.getJSONObject("user");
            name = user.getString("name");
            surnames = user.getString("surnames");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
