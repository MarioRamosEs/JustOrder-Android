package client.marpolex.com.justorder_android.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import client.marpolex.com.justorder_android.R;

public class TableActivity extends AppCompatActivity {

    int restaurantId, tableId;

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

        Log.d("Mesa", "restaurantId: "+restaurantId+" - tableId: "+tableId);

        TextView tvNumMesa = findViewById(R.id.tvNumMesa);
        tvNumMesa.setText(getString(R.string.table)+tableId);
    }

}
