package client.marpolex.com.justorder_android.Fragments;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.List;

import client.marpolex.com.justorder_android.Adapters.RestaurantsAdapter;
import client.marpolex.com.justorder_android.Models.Restaurant;
import client.marpolex.com.justorder_android.R;

/**
 * Created by mario on 22/03/2018.
 */

public class RestaurantsFragment extends Fragment {

    View myView;
    private RecyclerView recyclerView;
    private RestaurantsAdapter rAdapter;
    private List<Restaurant> restaurants;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.restaurantslist, container, false);
        onCreate();
        return myView;
    }

    public void onCreate(){
        restaurants = Restaurant.listAll(Restaurant.class);
        if(restaurants.size() == 0){ //DEBUG Carga los restaurantes de ejemplo
            loadSampleData();
            restaurants = Restaurant.listAll(Restaurant.class);
        }

        //Recycler view
        recyclerView = (RecyclerView) myView.findViewById(R.id.recycler_view);
        rAdapter = new RestaurantsAdapter(restaurants);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(rLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(rAdapter);

        rAdapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(MainActivity.this, VistaLugarActivity.class);
                //i.putExtra("id", (long) recyclerView.getChildAdapterPosition(v));
                //startActivity(i);
                Bundle args = new Bundle();
                long idRestaurant = rAdapter.getItemId(recyclerView.getChildAdapterPosition(v));
                args.putLong("idRestaurant", idRestaurant);
                Fragment restaurantFragment = new RestaurantFragment();
                restaurantFragment.setArguments(args);
                
                getFragmentManager().beginTransaction().replace(R.id.content_frame, restaurantFragment).addToBackStack( "tag" ).commit();
            }
        });
        //End Recycler view
    }

    public void loadSampleData(){
        Restaurant restaurant = new Restaurant("Nombre", "Direccion", "horas");
        restaurant.save();
        Restaurant restaurant2 = new Restaurant("Nombre2", "Direccion2", "horas2");
        restaurant2.save();
        Restaurant restaurant3 = new Restaurant("Nombre3", "Direccion3", "horas3");
        restaurant3.save();
        Log.d("SugarORM", "Sample data loaded.");
    }
}
