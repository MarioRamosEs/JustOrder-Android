package client.marpolex.com.justorder_android.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import client.marpolex.com.justorder_android.API.justOrderApiInterface;
import client.marpolex.com.justorder_android.Activities.TableActivity;
import client.marpolex.com.justorder_android.Models.Order;
import client.marpolex.com.justorder_android.Models.Singleton.justOrderApiConnectorClient;
import client.marpolex.com.justorder_android.R;

public class PayActivity extends AppCompatActivity implements justOrderApiInterface {

    private Button btnReturn;
    private TextView tvStatus;
    ProgressDialog dialogLoding;

    private ArrayList<Order> orderList;
    private int tableId, restaurantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        //Obtener datos
        tableId = getIntent().getIntExtra("tableId", -1);
        restaurantId = getIntent().getIntExtra("restaurantId", -1);
        orderList = (ArrayList<Order>) getIntent().getSerializableExtra("ordersToPay");

        //Llamada a la API
        justOrderApiConnectorClient.getJustOrderApiConnector().attemptPay(restaurantId, tableId, orderList, this);

        dialogLoding = ProgressDialog.show(this, "", "Cargando, por favor espere...", true);

        tvStatus = findViewById(R.id.tvStatus);
        tvStatus.setText("");
        btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToTable();
            }
        });
        btnReturn.setEnabled(false);
    }

    private void returnToTable() {
        //Ver la mesa
        /*Intent intent = new Intent(this, TableActivity.class);
        intent.putExtra("restaurantId", restaurantId);
        intent.putExtra("tableId", tableId);
        startActivity(intent);*/

        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void attemptPay_response(String jsonResponse) {
        //TODO
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

    }


}
