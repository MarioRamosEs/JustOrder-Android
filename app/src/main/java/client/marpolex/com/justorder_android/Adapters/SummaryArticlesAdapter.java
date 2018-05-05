package client.marpolex.com.justorder_android.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import client.marpolex.com.justorder_android.Models.Article;
import client.marpolex.com.justorder_android.Models.ShoppingCartClient;
import client.marpolex.com.justorder_android.R;

import static android.content.ContentValues.TAG;

public class SummaryArticlesAdapter extends RecyclerView.Adapter<SummaryArticlesAdapter.MyViewHolder> {
    protected View.OnClickListener onClickListener;
    private List<Article> articleList;

    public SummaryArticlesAdapter(List<Article> articleList) {
        this.articleList = articleList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_summary_row, parent, false);
        itemView.setOnClickListener(onClickListener);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Article article = articleList.get(position);
        int quantity = ShoppingCartClient.getShoppingCart().getQuantity(article);

        holder.name.setText(article.getName());
        holder.description.setText(article.getDescription());
        holder.pvp.setText(article.getBase_price() * quantity + "€");
        holder.uds.setText(quantity+"");

        Picasso.get().load(article.getImage()).placeholder(R.drawable.logo).into(holder.img);

        holder.removeFromCart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "onClick: TODO REMOVE FROM CART"); //todo remove from cart
            }
        });
        holder.plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "onClick: TODO PLUS"); //todo plus
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "onClick: TODO MINUS"); //todo minus
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
        public TextView name, description, pvp, uds;
        public ImageView img, removeFromCart, plus, minus;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tvName);
            description = (TextView) view.findViewById(R.id.tvDescription);
            pvp = (TextView) view.findViewById(R.id.tvPVP);
            img = (ImageView) view.findViewById(R.id.ivImg);
            removeFromCart = (ImageView) view.findViewById(R.id.ivRemoveFromCart);
            plus = (ImageView) view.findViewById(R.id.ivPlus);
            minus = (ImageView) view.findViewById(R.id.ivMinus);
            uds = (TextView) view.findViewById(R.id.tvUds);
        }
    }
}
