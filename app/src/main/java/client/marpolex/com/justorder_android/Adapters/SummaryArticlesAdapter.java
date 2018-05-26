package client.marpolex.com.justorder_android.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import client.marpolex.com.justorder_android.Activities.Carta.cartSummaryActivity;
import client.marpolex.com.justorder_android.Models.Article;
import client.marpolex.com.justorder_android.Models.Singleton.ShoppingCartClient;
import client.marpolex.com.justorder_android.R;

import static android.content.ContentValues.TAG;

public class SummaryArticlesAdapter extends RecyclerView.Adapter<SummaryArticlesAdapter.MyViewHolder> {
    protected View.OnClickListener onClickListener;
    private List<Article> articleList;
    private Context mContext;

    public SummaryArticlesAdapter(List<Article> articleList, Context context) {
        this.articleList = articleList;
        this.mContext = context;
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
        //holder.description.setText(article.getDescription());
        holder.pvp.setText(String.format("%.2f", article.getBase_price()*quantity)+"€");
        holder.uds.setText(quantity + "");

        if (!article.getImage().isEmpty())
            Picasso.get().load(article.getImage()).placeholder(R.drawable.logo).into(holder.img);

        holder.removeFromCart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ShoppingCartClient.getShoppingCart().removeArticle(article);
                if(mContext instanceof cartSummaryActivity){
                    ((cartSummaryActivity)mContext).loadRecycler();
                }
            }
        });
        holder.plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int newQuantity = ShoppingCartClient.getShoppingCart().getQuantity(article) + 1;
                ShoppingCartClient.getShoppingCart().setQuantity(article, newQuantity);
                //Toast.makeText(v.getContext(), "Cantidad: " + newQuantity, Toast.LENGTH_SHORT).show();
                if(mContext instanceof cartSummaryActivity){
                    ((cartSummaryActivity)mContext).loadRecycler();
                }
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int newQuantity = ShoppingCartClient.getShoppingCart().getQuantity(article) - 1;
                ShoppingCartClient.getShoppingCart().setQuantity(article, newQuantity);
                //Toast.makeText(v.getContext(), "Cantidad: " + newQuantity, Toast.LENGTH_SHORT).show();
                if(mContext instanceof cartSummaryActivity){
                    ((cartSummaryActivity)mContext).loadRecycler();
                }
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
        public TextView name, pvp, uds;
        public ImageView img, removeFromCart, plus, minus;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tvName);
            //description = (TextView) view.findViewById(R.id.tvDescription);
            pvp = (TextView) view.findViewById(R.id.tvPVP);
            img = (ImageView) view.findViewById(R.id.ivImg);
            removeFromCart = (ImageView) view.findViewById(R.id.ivRemoveFromCart);
            plus = (ImageView) view.findViewById(R.id.ivPlus);
            minus = (ImageView) view.findViewById(R.id.ivMinus);
            uds = (TextView) view.findViewById(R.id.tvUds);
        }
    }
}
