package client.marpolex.com.justorder_android.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import client.marpolex.com.justorder_android.Adapters.CategoriesAdapter;
import client.marpolex.com.justorder_android.Models.Article;
import client.marpolex.com.justorder_android.Models.Category;
import client.marpolex.com.justorder_android.Models.Subcategory;
import client.marpolex.com.justorder_android.R;

public class MenuActivity extends AppCompatActivity {

    List<Category> categoryList = loadData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final CategoriesAdapter cAdapter = new CategoriesAdapter(this, categoryList);
        final GridView lstElements = (GridView) findViewById(R.id.gvCategory);
        lstElements.setAdapter(cAdapter);

        lstElements.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MenuActivity.this, CategoryActivity.class);

                Category category =  ((Category) adapterView.getItemAtPosition(i));
                Bundle args = new Bundle();
                args.putSerializable("category", category);
                intent.putExtras(args);

                startActivity(intent);
            }
        });
    }

    private List<Category> loadData(){
        //TODO load real Categories
        Article amstelOro = new Article(0, "Amstel Oro", "330ml - 6.2% Alc", (float)2.99, "http://marf.es/justOrderTemp/AmstelOro.png", 4);
        Article amstelPlata = new Article(1, "Amstel Plata", "330ml - 666% Alc", (float)6.66, "http://marf.es/justOrderTemp/AmstelOro.png", 2);
        List<Article> tostadas = new ArrayList<>();
        tostadas.add(amstelOro); tostadas.add(amstelPlata);

        Subcategory scTostadas = new Subcategory(0, "Tostadas", tostadas);
        List<Subcategory> cervezas = new ArrayList<>();
        cervezas.add(scTostadas);

        Category cCervezas = new Category(0, "Cervezas", "http://marf.es/justOrderTemp/beer.png", cervezas);
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(cCervezas);

        return categoryList;
    }
}
