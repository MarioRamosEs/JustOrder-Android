package client.marpolex.com.justorder_android.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import client.marpolex.com.justorder_android.API.justOrderApiInterface;
import client.marpolex.com.justorder_android.Activities.Carta.MenuActivity;
import client.marpolex.com.justorder_android.Models.Singleton.justOrderApiConnectorClient;
import client.marpolex.com.justorder_android.R;

public class TableActivity extends AppCompatActivity implements justOrderApiInterface {

    int restaurantId, tableId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Estado de la mesa");

        //Obtener datos
        restaurantId = getIntent().getIntExtra("restaurantId", -1);
        tableId = getIntent().getIntExtra("tableId", -1);
        //End obtener datos

        //Llamada a la API
        justOrderApiConnectorClient.getJustOrderApiConnector().attemptGetTable(restaurantId, tableId, this);

        Log.d("Mesa", "restaurantId: "+restaurantId+" - tableId: "+tableId);

        TextView tvNumMesa = findViewById(R.id.tvNumMesa);
        tvNumMesa.setText(getString(R.string.table)+tableId);

        Button newOrder = findViewById(R.id.btnNewOrder);
        newOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuActivity.class);
                intent.putExtra("restaurantId", restaurantId);
                intent.putExtra("tableId", tableId);
                startActivity(intent);
            }
        });
    }

    private void updateRecyclerView(){

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
    }
}
