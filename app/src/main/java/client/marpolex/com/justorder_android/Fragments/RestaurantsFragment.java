package client.marpolex.com.justorder_android.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import client.marpolex.com.justorder_android.Adapters.RestaurantsAdapter;
import client.marpolex.com.justorder_android.Models.Category;
import client.marpolex.com.justorder_android.Models.Restaurant;
import client.marpolex.com.justorder_android.R;

import static android.content.ContentValues.TAG;

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

    public void onCreate() {
        try {
            restaurants = Restaurant.listAll(Restaurant.class);
        }catch (Exception e){
            loadRestaurants();
            restaurants = Restaurant.listAll(Restaurant.class);
        }

        if (restaurants.size() == 0) { //DEBUG Carga los restaurantes de ejemplo
            //loadSampleData();
            loadRestaurants();
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
                Bundle args = new Bundle();
                long idRestaurant = rAdapter.getItemId(recyclerView.getChildAdapterPosition(v));
                args.putLong("idRestaurant", idRestaurant);
                Fragment restaurantFragment = new RestaurantFragment();
                restaurantFragment.setArguments(args);

                getFragmentManager().beginTransaction().replace(R.id.content_frame, restaurantFragment).addToBackStack("tag").commit();
            }
        });
        //End Recycler view
    }

    /*public void loadSampleData() {
        Restaurant restaurant = new Restaurant("Taverna Serengeti", "Muralla de Sant Llorenç, 16, 08302 Mataró", "18:00-02:00", "http://www.funcionaris.cat/Clientes/Imagenes/225/7.jpg", 4);
        restaurant.save();
        Restaurant restaurant2 = new Restaurant("The Drunk Monk", "Via Europa, 30, 08303 Mataró", "18:00-02:00", "https://media-cdn.tripadvisor.com/media/photo-s/03/5b/7a/e2/drunk-monk.jpg", 3.8f);
        restaurant2.save();
        Restaurant restaurant3 = new Restaurant("The Beer Mugs", "Carrer de Montserrat, 21, 08302 Mataró", "19:30-02:30", "https://i.imgur.com/TDJmFlM.jpg", 2.2f);
        restaurant3.save();
        Log.d("SugarORM", "Sample data loaded.");
    }*/

    private void loadRestaurants(){ //Carga los restaurantes de un JSON
        Log.d(TAG, "loadRestaurants: ");
        List<Restaurant> restaurantList = new ArrayList<Restaurant>();

        //Cargar JSON
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("Restaurants.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            json = null;
            Log.e("JSON", "Error al cargar el JSON de Restaurantes");
        }

        //Preparar el JSON Object
        JSONObject object = null; //Creamos un objeto JSON a partir de la cadena
        try {
            object = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray restaurants = null;
        try {
            restaurants = object.getJSONArray("Restaurants");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < restaurants.length(); i++) {
            try {
                restaurantList.add(new Restaurant(restaurants.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Restaurant.saveInTx(restaurantList);

        //restaurantList.get(0).save();
    }
}
