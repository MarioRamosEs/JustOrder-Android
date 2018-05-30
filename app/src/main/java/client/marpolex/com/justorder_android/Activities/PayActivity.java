package client.marpolex.com.justorder_android.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import client.marpolex.com.justorder_android.Activities.TableActivity;
import client.marpolex.com.justorder_android.Models.Order;
import client.marpolex.com.justorder_android.Models.Singleton.justOrderApiConnectorClient;
import client.marpolex.com.justorder_android.Models.User;
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
        //btnReturn.setEnabled(false);

        //Llamada a la API
        justOrderApiConnectorClient.getJustOrderApiConnector().attemptPay(restaurantId, tableId, orderToPayToString(), this);
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
        Log.d("attemptPay_response", jsonResponse);

        try {
            JSONObject response = new JSONObject(jsonResponse);
            boolean success = response.getBoolean("success");
            if (!success) {             //Login failed
                Toast.makeText(this.getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                tvStatus.setText("Pago fallido");
                unLockInterface();
            } else {                    //Username and password OK
                justOrderApiConnectorClient.getJustOrderApiConnector().clearCallbackActivity();
                tvStatus.setText("Pago aceptado");
                unLockInterface();

            }
        } catch (JSONException e) {     //JSON couldn't be parsed or no connection to api server
            Log.d("attemptLogin", e.toString());
            Toast.makeText(this.getApplicationContext(), "Error al conectar con la API", Toast.LENGTH_SHORT).show();
            unLockInterface();
        }
    }

    @Override
    public void sendRatingProduct_response(String jsonResponse) {

    }

    @Override
    public void sendRatingRestaurant_response(String jsonResponse) {

    }

    private void unLockInterface() {
        dialogLoding.hide();
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

    private String orderToPayToString(){
        JSONArray items = new JSONArray();
        for(Order order : orderList){
            JSONObject temp = new JSONObject();
            try {
                temp.put("content_id", order.getOrderId());
                temp.put("quantity", order.getQuantity());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            items.put(temp);
        }

        return items.toString();
    }
}
