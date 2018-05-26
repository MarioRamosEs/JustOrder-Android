package client.marpolex.com.justorder_android.Activities.Carta;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import client.marpolex.com.justorder_android.Adapters.SubcategoriesAdapter;
import client.marpolex.com.justorder_android.Models.Category;
import client.marpolex.com.justorder_android.Models.Subcategory;
import client.marpolex.com.justorder_android.R;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SubcategoriesAdapter scAdapter;
    private Category category;
    private List<Subcategory> subcategories;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        //Obtencion de datos
        Bundle b = getIntent().getExtras();
        category = (Category) b.getSerializable("category");
        subcategories = category.getSubcategories();
        //End obtencion de datos

        //TOOLBAR
        toolbar = (Toolbar) findViewById(R.id.tool_bar);  // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar); // Setting toolbar as the ActionBar with setSupportActionBar() call
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle(category.getName());
        //END TOOLBAR

        //Recycler view
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        scAdapter = new SubcategoriesAdapter(subcategories);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(rLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(scAdapter);

        scAdapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long idSubcategory = scAdapter.getItemId(recyclerView.getChildAdapterPosition(v));

                Intent i = new Intent(CategoryActivity.this, SubcategoryActivity.class);

                Subcategory subcategory = subcategories.get((int) idSubcategory);
                Bundle args = new Bundle();
                args.putSerializable("subcategory", subcategory);
                i.putExtras(args);

                startActivity(i);
            }
        });
        //End Recycler view
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
            Intent intent = new Intent(CategoryActivity.this, cartSummaryActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
