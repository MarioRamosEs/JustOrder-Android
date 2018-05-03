package client.marpolex.com.justorder_android.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import client.marpolex.com.justorder_android.Models.Category;
import client.marpolex.com.justorder_android.R;

/**
 * Created by mario on 08/04/2018.
 */

public class CategoriesAdapter extends ArrayAdapter<Category> {
    List<Category> categories;
    Context context;

    public CategoriesAdapter(Context context, List<Category> categories) {
        super(context, R.layout.category_grid, categories);
        this.categories = categories;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.category_grid, null);

        TextView name = (TextView) item.findViewById(R.id.tvCategoryName);
        ImageView img = (ImageView) item.findViewById(R.id.ivCategoryImg);

        name.setText(categories.get(position).getName());
        Picasso.get().load(categories.get(position).getImg()).placeholder(R.drawable.logo).into(img);

        return item;
    }
}
