package client.marpolex.com.justorder_android.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import client.marpolex.com.justorder_android.Models.Restaurant;
import client.marpolex.com.justorder_android.R;

/**
 * Created by mario on 28/03/2018.
 */

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.MyViewHolder> {
    private List<Restaurant> restaurantsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, direction, openingHours;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tvName);
            direction = (TextView) view.findViewById(R.id.tvDirection);
            openingHours = (TextView) view.findViewById(R.id.tvOpeningHours);
        }
    }

    public RestaurantsAdapter(List<Restaurant> restaurantsList) {
        this.restaurantsList = restaurantsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Restaurant restaurant = restaurantsList.get(position);
        holder.name.setText(restaurant.getName());
        holder.direction.setText(restaurant.getDirection());
        holder.openingHours.setText(restaurant.getOpeningHours());
    }

    @Override
    public int getItemCount() {
        return restaurantsList.size();
    }
}
