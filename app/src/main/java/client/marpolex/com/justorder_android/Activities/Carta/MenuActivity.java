package client.marpolex.com.justorder_android.Activities.Carta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import client.marpolex.com.justorder_android.Adapters.CategoriesAdapter;
import client.marpolex.com.justorder_android.Models.Category;
import client.marpolex.com.justorder_android.R;

public class MenuActivity extends AppCompatActivity {

    List<Category> categoryList;
    int idRestaurant = 0;
    String nameRestaurant = "Restaurant test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        categoryList = loadData(idRestaurant); //Toda la carta

        /*Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        setSu
        getSupportActionBar().setTitle("My title");

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(nameRestaurant);*/

        final CategoriesAdapter cAdapter = new CategoriesAdapter(this, categoryList);
        final GridView lstElements = (GridView) findViewById(R.id.gvCategory);
        lstElements.setAdapter(cAdapter);

        lstElements.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MenuActivity.this, CategoryActivity.class);

                Category category = ((Category) adapterView.getItemAtPosition(i));
                Bundle args = new Bundle();
                args.putSerializable("category", category);
                intent.putExtras(args);

                startActivity(intent);
            }
        });
    }

    private List<Category> loadData(int idRestaurant) { //TODO cargar id Restaurant
        List<Category> categoryList = new ArrayList<Category>();

        //Cargar JSON
        String json = null;
        try {
            InputStream is = getAssets().open("Restaurant_1_cart.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            json = null;
            Log.e("JSON", "Error al cargar el JSON");
        }

        //Preparar el JSON Object
        JSONObject object = null; //Creamos un objeto JSON a partir de la cadena
        try {
            object = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray categories = null;
        try {
            JSONObject products = object.getJSONObject("products");
            categories = products.getJSONArray("types");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < categories.length(); i++) {
            try {
                categoryList.add(new Category(categories.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return categoryList;
    }
}
