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
    private String imgUrl;

    Rating(JSONObject jsonObject, long id){
        this.id = id;
        try {
            idRating = jsonObject.getInt("id");
            rating = jsonObject.getDouble("rating");
            createdAt = jsonObject.getString("created_at");

            JSONObject user = jsonObject.getJSONObject("user");
            name = user.getString("name");
            surnames = user.getString("surnames");
            imgUrl = user.getString("img");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public long getId() {
        return id;
    }

    public int getIdRating() {
        return idRating;
    }

    public double getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getName() {
        return name;
    }

    public String getSurnames() {
        return surnames;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
