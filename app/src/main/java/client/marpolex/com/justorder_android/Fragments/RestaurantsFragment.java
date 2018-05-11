package client.marpolex.com.justorder_android.Fragments;

import android.app.ProgressDialog;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import client.marpolex.com.justorder_android.API.justOrderApiConnector;
import client.marpolex.com.justorder_android.API.justOrderApiInterface;
import client.marpolex.com.justorder_android.Adapters.RestaurantsAdapter;
import client.marpolex.com.justorder_android.Models.Restaurant;
import client.marpolex.com.justorder_android.Models.Singleton.justOrderApiConnectorClient;
import client.marpolex.com.justorder_android.R;

import static android.content.ContentValues.TAG;

public class RestaurantsFragment extends Fragment implements justOrderApiInterface {

    View myView;
    ProgressDialog dialogLoding;
    private RecyclerView recyclerView;
    private RestaurantsAdapter rAdapter;
    private List<Restaurant> restaurants;
    private static justOrderApiConnector apiConnector;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.restaurantslist, container, false);
        onCreate();
        return myView;
    }

    public void onCreate() {
        dialogLoding = ProgressDialog.show(getActivity(), "", "Cargando, por favor espere...", true);
        try {
            restaurants = Restaurant.listAll(Restaurant.class);
        } catch (Exception e) {
            loadRestaurantsApi();
            restaurants = Restaurant.listAll(Restaurant.class);
        }

        if (restaurants.size() == 0) {
            loadRestaurantsApi();
            restaurants = Restaurant.listAll(Restaurant.class);
        }

        loadRestaurantsInRecycler();
    }

    private void loadRestaurantsInRecycler() {
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

        dialogLoding.hide();
    }

    private void loadRestaurants(String jsonResponse) { //Carga los restaurantes de un JSON
        Log.d(TAG, "loadRestaurants: ");
        List<Restaurant> restaurantList = new ArrayList<Restaurant>();

        //Leer JSON
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("Restaurants_test.json");
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

        /*
        //Preparar el JSON Object
        JSONObject object = null; //Creamos un objeto JSON a partir de la cadena
        try {
            object = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        */

        //Preparar JSON Array
        JSONArray restaurants = null;
        try {
            restaurants = new JSONArray(json);

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
    }

    private void loadRestaurantsApi() {
        apiConnector = justOrderApiConnectorClient.getJustOrderApiConnector();
        apiConnector.getRestaurants(this);
    }

    @Override
    public void attemptLogin_response(String jsonResponse) {

    }

    @Override
    public void attemptRegister_response(String jsonResponse) {

    }

    @Override
    public void getRestaurants_response(String jsonResponse) {
        Log.d("getRestaurants_response", jsonResponse);
        loadRestaurants(jsonResponse);
        loadRestaurantsInRecycler();
    }
}
