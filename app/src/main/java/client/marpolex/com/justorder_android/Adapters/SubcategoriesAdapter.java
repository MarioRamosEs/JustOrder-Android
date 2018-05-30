package client.marpolex.com.justorder_android.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import client.marpolex.com.justorder_android.Models.Subcategory;
import client.marpolex.com.justorder_android.R;

public class SubcategoriesAdapter extends RecyclerView.Adapter<SubcategoriesAdapter.MyViewHolder> {
    protected View.OnClickListener onClickListener;
    private List<Subcategory> subcategoryList;

    public SubcategoriesAdapter(List<Subcategory> subcategoryList) {
        this.subcategoryList = subcategoryList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcategory_row, parent, false);
        itemView.setOnClickListener(onClickListener);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Subcategory subcategory = subcategoryList.get(position);
        holder.name.setText(subcategory.getName());

        if(! subcategory.getImage().isEmpty())
            Picasso.get().load(subcategory.getImage()).placeholder(R.drawable.logo).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return subcategoryList.size();
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public long getItemId(int position) {
        return subcategoryList.get(position).getId();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, direction, openingHours;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tvName);
            imageView = view.findViewById(R.id.ivImg2);
        }
    }
}
