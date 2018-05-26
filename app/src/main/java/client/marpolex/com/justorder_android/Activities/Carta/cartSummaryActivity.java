package client.marpolex.com.justorder_android.Activities.Carta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.CrashUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import client.marpolex.com.justorder_android.API.justOrderApiConnector;
import client.marpolex.com.justorder_android.API.justOrderApiInterface;
import client.marpolex.com.justorder_android.Activities.LoginActivity;
import client.marpolex.com.justorder_android.Activities.MainActivity;
import client.marpolex.com.justorder_android.Activities.TableActivity;
import client.marpolex.com.justorder_android.Adapters.SummaryArticlesAdapter;
import client.marpolex.com.justorder_android.Models.Article;
import client.marpolex.com.justorder_android.Models.ArticleSummary;
import client.marpolex.com.justorder_android.Models.Singleton.ShoppingCart;
import client.marpolex.com.justorder_android.Models.Singleton.ShoppingCartClient;
import client.marpolex.com.justorder_android.Models.Singleton.justOrderApiConnectorClient;
import client.marpolex.com.justorder_android.Models.User;
import client.marpolex.com.justorder_android.R;

public class cartSummaryActivity extends AppCompatActivity implements justOrderApiInterface {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private SummaryArticlesAdapter summaryArticlesAdapter;

    private ShoppingCart shoppingCart; //Singleton
    private static justOrderApiConnector apiConnector;

    ProgressDialog dialogLoding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_summary);

        //Instanciate a new apiConnector
        apiConnector = justOrderApiConnectorClient.getJustOrderApiConnector();

        //TOOLBAR
        toolbar = (Toolbar) findViewById(R.id.tool_bar);  // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar); // Setting toolbar as the ActionBar with setSupportActionBar() call
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle(R.string.cartSummary);
        //END TOOLBAR

        loadRecycler();

        //Boton aceptar pedido
        Button btnSendCommand = findViewById(R.id.btnSendCommand);
        btnSendCommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               attemptOrder();
            }
        });
        //End boton aceptar pedido
    }

    public void loadRecycler(){
        //Obtencion de datos
        shoppingCart = ShoppingCartClient.getShoppingCart();
        //End obtencion de datos

        //Recycler view
        List<Article> articleList = new ArrayList<>();
        articleList.addAll(shoppingCart.getShoppingMap().keySet());
        summaryArticlesAdapter = new SummaryArticlesAdapter(articleList, this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(rLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(summaryArticlesAdapter);

        TextView tvTotalPrice = findViewById(R.id.tvTotalPrice);
        float totalPrice = ShoppingCartClient.getShoppingCart().getTotalPrice();
        tvTotalPrice.setText("Total: "+String.format("%.2f", totalPrice)+"€");
        //End Recycler view
    }

    private void attemptOrder() {
        dialogLoding = ProgressDialog.show(cartSummaryActivity.this, "", "Cargando, por favor espere...", true);
        dialogLoding.show();
        apiConnector.attemptOrder(this);
    }

    @Override
    public void attemptLogin_response(String jsonResponse) {

    }

    @Override
    public void attemptRegister_response(String jsonResponse) {

    }

    @Override
    public void getRestaurants_response(String jsonResponse) {

    }

    @Override
    public void getCatalog_response(String jsonResponse) {

    }

    @Override
    public void attemptGetTable_response(String jsonResponse) {

    }

    @Override
    public void attemptPay_response(String jsonResponse) {

    }

    @Override
    public void attemptOrder_response(String jsonResponse) {
        Log.d("attemptOrder_response: ", jsonResponse);

        try {
            JSONObject response = new JSONObject(jsonResponse);
            boolean success = response.getBoolean("success");

            if (!success) {             //Order failed
                Toast.makeText(this.getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                dialogLoding.hide();
            } else {                    //Order OK
                Toast.makeText(this.getApplicationContext(), "Pedido realizado correctamente.", Toast.LENGTH_SHORT).show();
                apiConnector.clearCallbackActivity();
                int idTable = shoppingCart.getTableId();
                int idRestaurant = shoppingCart.getRestaurantId();
                ShoppingCartClient.resetShoppingCart();

                //Ver la mesa
                Intent intent = new Intent(this, TableActivity.class);
                intent.putExtra("restaurantId", idRestaurant);
                intent.putExtra("tableId", idTable);
                startActivity(intent);
            }
            Toast.makeText(this.getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {     //JSON couldn't be parsed or no connection to api server
            Log.d("attemptLogin", e.toString());
            Toast.makeText(this.getApplicationContext(), "Error al conectar con la API", Toast.LENGTH_SHORT).show();
            dialogLoding.hide();
        }
    }
}