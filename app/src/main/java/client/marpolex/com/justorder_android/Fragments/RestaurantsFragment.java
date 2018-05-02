package client.marpolex.com.justorder_android.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public void onCreate() {
        restaurants = Restaurant.listAll(Restaurant.class);
        if (restaurants.size() == 0) { //DEBUG Carga los restaurantes de ejemplo
            loadSampleData();
            restaurants = Restaurant.listAll(Restaurant.class);
        }

        //Recycler view
        recyclerView = (RecyclerView) myView.findViewById(R.id.recycler_view);
        rAdapter = new RestaurantsAdapter(restaurants);
        //RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        //recyclerView.setLayoutManager(rLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
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

    public void loadSampleData() {
        Restaurant restaurant = new Restaurant("Taverna Serengeti", "Muralla de Sant Llorenç, 16, 08302 Mataró", "18:00-02:00", "http://www.funcionaris.cat/Clientes/Imagenes/225/7.jpg", 4);
        restaurant.save();
        Restaurant restaurant2 = new Restaurant("The Drunk Monk", "Via Europa, 30, 08303 Mataró", "18:00-02:00", "https://media-cdn.tripadvisor.com/media/photo-s/03/5b/7a/e2/drunk-monk.jpg", 3.8f);
        restaurant2.save();
        Restaurant restaurant3 = new Restaurant("The Beer Mugs", "Carrer de Montserrat, 21, 08302 Mataró", "19:30-02:30", "https://i.imgur.com/TDJmFlM.jpg", 2.2f);
        restaurant3.save();
        Log.d("SugarORM", "Sample data loaded.");
    }
}
