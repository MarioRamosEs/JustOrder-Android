package client.marpolex.com.justorder_android.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import client.marpolex.com.justorder_android.Models.Article;
import client.marpolex.com.justorder_android.Models.Rating;
import client.marpolex.com.justorder_android.Models.Singleton.ShoppingCart;
import client.marpolex.com.justorder_android.Models.Singleton.ShoppingCartClient;
import client.marpolex.com.justorder_android.R;

public class RatingsAdapter extends RecyclerView.Adapter<RatingsAdapter.MyViewHolder> {
    protected View.OnClickListener onClickListener;
    private List<Rating> ratingList;

    public RatingsAdapter(List<Rating> ratingList) {
        this.ratingList = ratingList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rating_row, parent, false);
        itemView.setOnClickListener(onClickListener);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Rating rating = ratingList.get(position);

        holder.name.setText(rating.getName() + " " + rating.getSurnames());
        holder.description.setText(rating.getComment());
        holder.ratingBar.setRating((float) rating.getRating());

        if (!rating.getImgUrl().isEmpty())
            Picasso.get().load(rating.getImgUrl()).placeholder(R.drawable.logo).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return ratingList.size();
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public long getItemId(int position) {
        return ratingList.get(position).getId();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description;
        public ImageView img;
        public RatingBar ratingBar;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tvName);
            description = view.findViewById(R.id.tvDescription);
            img = view.findViewById(R.id.ivImg);
            ratingBar = view.findViewById(R.id.ratingBar);
        }
    }
}
