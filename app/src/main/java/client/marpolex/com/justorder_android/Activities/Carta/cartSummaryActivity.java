package client.marpolex.com.justorder_android.Activities.Carta;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import client.marpolex.com.justorder_android.Activities.MainActivity;
import client.marpolex.com.justorder_android.Adapters.ArticlesAdapter;
import client.marpolex.com.justorder_android.Adapters.SummaryArticlesAdapter;
import client.marpolex.com.justorder_android.Models.Article;
import client.marpolex.com.justorder_android.Models.ShoppingCartClient;
import client.marpolex.com.justorder_android.Models.Subcategory;
import client.marpolex.com.justorder_android.R;

public class cartSummaryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private static Map<Article, Integer> shoppingMap;
    private RecyclerView recyclerView;
    private SummaryArticlesAdapter summaryArticlesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_summary);

        //TOOLBAR
        toolbar = (Toolbar) findViewById(R.id.tool_bar);  // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar); // Setting toolbar as the ActionBar with setSupportActionBar() call
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle(R.string.cartSummary);
        //END TOOLBAR

        //Obtencion de datos
        shoppingMap = ShoppingCartClient.getShoppingCart().getShoppingMap();
        //End obtencion de datos

        //Recycler view
        List<Article> articleList = new ArrayList<>();
        articleList.addAll(shoppingMap.keySet());
        summaryArticlesAdapter = new SummaryArticlesAdapter(articleList);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(rLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(summaryArticlesAdapter);

        TextView tvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);
        float totalPrice = ShoppingCartClient.getShoppingCart().getTotalPrice();
        tvTotalPrice.setText(getString(R.string.totalPrice)+" "+totalPrice+"€");
        //End Recycler view

        //Boton aceptar pedido
        Button btnSendCommand = (Button) findViewById(R.id.btnSendCommand);
        btnSendCommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Enviar comanda
                Intent intent = new Intent(cartSummaryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //End boton aceptar pedido
    }
}
