package client.marpolex.com.justorder_android.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import client.marpolex.com.justorder_android.Models.Restaurant;
import client.marpolex.com.justorder_android.R;

/**
 * Created by mario on 22/03/2018.
 */

public class RestaurantFragment extends Fragment {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.restaurantslist, container, false);
        onCreate();
        return myView;
    }

    public void onCreate(){
        List<Restaurant> restaurants = Restaurant.listAll(Restaurant.class);
        if(restaurants.size() == 0) loadSampleData();
        Log.d("SugarORM", restaurants.size()+"");
    }

    public void loadSampleData(){
        Restaurant restaurant = new Restaurant(1, "Nombre", "Direccion", "horas");
        restaurant.save();
        Restaurant restaurant2 = new Restaurant(2, "Nombre2", "Direccion2", "horas2");
        restaurant2.save();
        Restaurant restaurant3 = new Restaurant(3, "Nombre3", "Direccion3", "horas3");
        restaurant3.save();
        Log.d("SugarORM", "Sample data loaded.");
    }
}
