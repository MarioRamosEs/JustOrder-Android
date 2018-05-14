package client.marpolex.com.justorder_android.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import client.marpolex.com.justorder_android.Models.Article;
import client.marpolex.com.justorder_android.Models.Singleton.ShoppingCart;
import client.marpolex.com.justorder_android.Models.Singleton.ShoppingCartClient;
import client.marpolex.com.justorder_android.R;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.MyViewHolder> {
    protected View.OnClickListener onClickListener;
    private List<Article> articleList;

    public ArticlesAdapter(List<Article> articleList) {
        this.articleList = articleList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_row, parent, false);
        itemView.setOnClickListener(onClickListener);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Article article = articleList.get(position);

        Log.d("Articulo", article.toString());

        holder.name.setText(article.getName());
        holder.description.setText(article.getDescription());
        holder.pvp.setText(article.getBase_price() + "€");

        if(! article.getImage().isEmpty())
            Picasso.get().load(article.getImage()).placeholder(R.drawable.logo).into(holder.img);

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ShoppingCart shoppingCart = ShoppingCartClient.getShoppingCart();
                shoppingCart.addArticle(article);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public long getItemId(int position) {
        return articleList.get(position).getId();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description, pvp;
        public ImageView img, addToCart;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tvName);
            description = (TextView) view.findViewById(R.id.tvDescription);
            pvp = (TextView) view.findViewById(R.id.tvPVP);
            img = (ImageView) view.findViewById(R.id.ivImg);
            addToCart = (ImageView) view.findViewById(R.id.ivAddToCart);
        }
    }
}
