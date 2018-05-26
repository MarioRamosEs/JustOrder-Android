package client.marpolex.com.justorder_android.Activities.Carta;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import client.marpolex.com.justorder_android.API.justOrderApiConnector;
import client.marpolex.com.justorder_android.API.justOrderApiInterface;
import client.marpolex.com.justorder_android.Adapters.CategoriesAdapter;
import client.marpolex.com.justorder_android.Models.Category;
import client.marpolex.com.justorder_android.Models.Restaurant;
import client.marpolex.com.justorder_android.Models.Singleton.ShoppingCartClient;
import client.marpolex.com.justorder_android.Models.Singleton.justOrderApiConnectorClient;
import client.marpolex.com.justorder_android.R;

public class MenuActivity extends AppCompatActivity implements justOrderApiInterface {

    List<Category> categoryList;
    int idRestaurant, idTable;
    private Toolbar toolbar;
    private Restaurant restaurant;
    private static justOrderApiConnector apiConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Obtener datos
        idRestaurant = getIntent().getIntExtra("restaurantId", -1);
        idTable = getIntent().getIntExtra("tableId", -1);
        Log.d("idRestaurant", String.valueOf(idRestaurant));
        restaurant = Restaurant.find(Restaurant.class, "id_restaurant = ?", idRestaurant+"").get(0);

        //Establecer el idRestaurant y idTable en ShoppingCart
        ShoppingCartClient.getShoppingCart().setRestaurantId(idRestaurant);
        ShoppingCartClient.getShoppingCart().setTableId(idTable);

        //TOOLBAR
        toolbar = (Toolbar) findViewById(R.id.tool_bar);  // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar); // Setting toolbar as the ActionBar with setSupportActionBar() call
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle(restaurant.getName());
        //END TOOLBAR

        attemptGetCart();
    }

    private void attemptGetCart(){
        //TODO cargando
        apiConnector = justOrderApiConnectorClient.getJustOrderApiConnector();
        apiConnector.attemptGetCatalog(idRestaurant, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {
            Intent intent = new Intent(MenuActivity.this, cartSummaryActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_search) {
            //Todo Busqueda
            Log.d("TODO", "onOptionsItemSelected: Busqueda TODO");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<Category> loadData(String jsonResponse) { //TODO cargar id Restaurant
        List<Category> categoryList = new ArrayList<Category>();

        /*
        //Cargar JSON
        String json = null;
        try {
            InputStream is = getAssets().open("Restaurant_" + idRestaurant + "_cart.json");
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
        */

        //Preparar el JSON Object
        JSONObject object = null; //Creamos un objeto JSON a partir de la cadena
        try {
            object = new JSONObject(jsonResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray categories = null;
        try {
            JSONObject products = object.getJSONObject("catalog").getJSONObject("products");
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
        Log.d("getCatalog_response: ", jsonResponse);
        categoryList = loadData(jsonResponse);
        updateRecycler();
    }

    @Override
    public void attemptGetTable_response(String jsonResponse) {

    }

    @Override
    public void attemptPay_response(String jsonResponse) {

    }

    @Override
    public void attemptOrder_response(String jsonResponse) {

    }

    private void updateRecycler(){
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
}
