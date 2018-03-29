package client.marpolex.com.justorder_android.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import client.marpolex.com.justorder_android.Adapters.RestaurantsAdapter;
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
        myView = inflater.inflate(R.layout.restaurant_fragment, container, false);
        onCreate();
        return myView;
    }

    public void onCreate(){
        long idRestaurant = getArguments().getLong("idRestaurant");
        loadRestaurantInfo(idRestaurant);
    }

    private void loadRestaurantInfo(long idRestaurant){
        Log.d("Loading restaurant", idRestaurant+"");

        Restaurant restaurant = Restaurant.findById(Restaurant.class, idRestaurant);

        TextView name = (TextView) myView.findViewById(R.id.tvName);
        TextView direction = (TextView) myView.findViewById(R.id.tvDirection);
        TextView openingHours = (TextView) myView.findViewById(R.id.tvOpeningHours);

        name.setText(restaurant.getName());
        direction.setText(restaurant.getDirection());
        openingHours.setText(restaurant.getOpeningHours());
    }
}
