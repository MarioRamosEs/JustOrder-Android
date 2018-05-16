package client.marpolex.com.justorder_android.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import client.marpolex.com.justorder_android.Models.Article;
import client.marpolex.com.justorder_android.Models.Order;
import client.marpolex.com.justorder_android.Models.Singleton.ShoppingCart;
import client.marpolex.com.justorder_android.Models.Singleton.ShoppingCartClient;
import client.marpolex.com.justorder_android.R;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyViewHolder> {
    protected View.OnClickListener onClickListener;
    private List<Order> orderList;

    public OrdersAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_row, parent, false);
        itemView.setOnClickListener(onClickListener);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Order order = orderList.get(position);
        final Article article = order.getProduct();

        holder.tvName.setText(article.getName());
        holder.tvPvp.setText(article.getBase_price()*order.getQuantity() + "€");
        holder.tvStatus.setText(order.getStatus());
        holder.tvPaid.setText(order.getPaid());
        holder.tvOrderedBy.setText(order.getOrderedBy());

        if (!article.getImage().isEmpty())
            Picasso.get().load(article.getImage()).placeholder(R.drawable.logo).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvStatus, tvPaid, tvOrderedBy, tvPvp;
        public ImageView img;

        public MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvStatus = view.findViewById(R.id.tvStatus);
            tvPaid = view.findViewById(R.id.tvPaid);
            tvOrderedBy = view.findViewById(R.id.tvOrderedBy);
            tvPvp = view.findViewById(R.id.tvPVP);
            img = view.findViewById(R.id.ivImg);
        }
    }
}
