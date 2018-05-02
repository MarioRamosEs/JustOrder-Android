package client.marpolex.com.justorder_android.Activities.Carta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        //Obtencion de datos
        Bundle b = getIntent().getExtras();
        category = (Category) b.getSerializable("category");
        subcategories = category.getSubcategories();
        //End obtencion de datos

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(category.getName());

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

                Subcategory subcategory = subcategories.get((int) idSubcategory - 1);
                Bundle args = new Bundle();
                args.putSerializable("subcategory", subcategory);
                i.putExtras(args);

                startActivity(i);
            }
        });
        //End Recycler view
    }
}
