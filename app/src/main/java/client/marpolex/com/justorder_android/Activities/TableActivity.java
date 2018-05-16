package client.marpolex.com.justorder_android.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import client.marpolex.com.justorder_android.API.justOrderApiInterface;
import client.marpolex.com.justorder_android.Activities.Carta.MenuActivity;
import client.marpolex.com.justorder_android.Adapters.OrdersAdapter;
import client.marpolex.com.justorder_android.Models.Order;
import client.marpolex.com.justorder_android.Models.Singleton.justOrderApiConnectorClient;
import client.marpolex.com.justorder_android.R;

public class TableActivity extends AppCompatActivity implements justOrderApiInterface {

    private ProgressDialog dialogLoding;
    private int restaurantId, tableId;
    private List<Order> orderList = new ArrayList<>();
    private RecyclerView recyclerView;
    private OrdersAdapter ordersAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Obtener datos
        restaurantId = getIntent().getIntExtra("restaurantId", -1);
        tableId = getIntent().getIntExtra("tableId", -1);
        //End obtener datos

        //Llamada a la API
        dialogLoding = ProgressDialog.show(TableActivity.this, "", "Cargando, por favor espere...", true);
        dialogLoding.show();
        justOrderApiConnectorClient.getJustOrderApiConnector().attemptGetTable(restaurantId, tableId, this);

        getSupportActionBar().setTitle(getString(R.string.table) + " " + tableId);

        Button newOrder = findViewById(R.id.btnNewOrder);
        newOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("new order", "onClick: btnNewOrder");
                Intent intent = new Intent(v.getContext(), MenuActivity.class);
                intent.putExtra("restaurantId", restaurantId);
                intent.putExtra("tableId", tableId);
                startActivity(intent);
            }
        });
    }

    private void updateOrderList(JSONArray jsonArray) {
        try {
            JSONArray tableContents = jsonArray.getJSONObject(0).getJSONArray("table_contents");
            for (int i = 0; i < tableContents.length(); i++) {
                orderList.add(new Order(tableContents.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateRecyclerView() {
        recyclerView = findViewById(R.id.rvOrders);
        ordersAdapter = new OrdersAdapter(orderList);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(rLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(ordersAdapter);
    }

    @Override
    public void attemptLogin_response(String jsonResponse) {

    }

    @Override
    public void attemptRegister_response(String jsonResponse) {

    }

    @Override
    public void attemptOrder_response(String jsonResponse) {

    }

    @Override
    public void getRestaurants_response(String jsonResponse) {

    }

    @Override
    public void getCatalog_response(String jsonResponse) {

    }

    @Override
    public void attemptGetTable_response(String jsonResponse) {
        Log.d("GetTable_response", jsonResponse);
        dialogLoding.hide();

        try {
            JSONObject response = new JSONObject(jsonResponse);
            boolean success = response.getBoolean("success");
            if (!success) {             //Response failed
                Toast.makeText(this.getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
            } else {                    //Response OK
                justOrderApiConnectorClient.getJustOrderApiConnector().clearCallbackActivity();
                updateOrderList(response.getJSONArray("tables"));
                updateRecyclerView();
            }
            Toast.makeText(this.getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {     //JSON couldn't be parsed or no connection to api server
            Log.d("GetTable_response", e.toString());
            Toast.makeText(this.getApplicationContext(), "Error al conectar con la API", Toast.LENGTH_SHORT).show();
        }
    }
}
