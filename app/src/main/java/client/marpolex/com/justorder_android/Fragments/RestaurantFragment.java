package client.marpolex.com.justorder_android.Fragments;

import android.content.Intent;
import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import client.marpolex.com.justorder_android.Activities.MenuActivity;
import client.marpolex.com.justorder_android.Models.Restaurant;
import client.marpolex.com.justorder_android.R;


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
        final long idRestaurant = getArguments().getLong("idRestaurant");
        loadRestaurantInfo(idRestaurant);

        Button menu = (Button) myView.findViewById(R.id.btnMenu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), MenuActivity.class);
                intent.putExtra("idRestaurant", idRestaurant);
                startActivity(intent);
            }
        });
    }

    private void loadRestaurantInfo(long idRestaurant){
        Log.d("Loading restaurant", idRestaurant+"");

        Restaurant restaurant = Restaurant.findById(Restaurant.class, idRestaurant);

        TextView name = (TextView) myView.findViewById(R.id.tvName);
        TextView direction = (TextView) myView.findViewById(R.id.tvDescription);
        TextView openingHours = (TextView) myView.findViewById(R.id.tvOpeningHours);
        ImageView imageView = (ImageView)  myView.findViewById(R.id.ivRestaurant);
        RatingBar ratingBar = (RatingBar)  myView.findViewById(R.id.ratingBar);

        name.setText(restaurant.getName());
        direction.setText(restaurant.getDirection());
        openingHours.setText(restaurant.getOpeningHours());

        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        Picasso.get().load(restaurant.getImgUrl()).placeholder(R.drawable.logo).resize(size.x, 700).into(imageView); //.centerCrop()

        ratingBar.setRating(restaurant.getRating());
    }
}
