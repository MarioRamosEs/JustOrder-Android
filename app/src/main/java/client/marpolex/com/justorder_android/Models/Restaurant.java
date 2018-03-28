package client.marpolex.com.justorder_android.Models;

import com.orm.SugarRecord;

/**
 * Created by mario on 28/03/2018.
 */

public class Restaurant extends SugarRecord<Restaurant> {
    long id;
    String name;
    String direction;
    String openingHours;

    public Restaurant(){
    }

    public Restaurant(long id, String name, String direction, String openingHours){
        this.id = id;
        this.name = name;
        this.direction = direction;
        this.openingHours = openingHours;
    }
}
