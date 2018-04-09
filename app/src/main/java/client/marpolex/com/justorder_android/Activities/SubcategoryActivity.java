package client.marpolex.com.justorder_android.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import client.marpolex.com.justorder_android.Adapters.SubcategoriesAdapter;
import client.marpolex.com.justorder_android.Models.Article;
import client.marpolex.com.justorder_android.Models.Category;
import client.marpolex.com.justorder_android.Models.Subcategory;
import client.marpolex.com.justorder_android.R;

public class SubcategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SubcategoriesAdapter scAdapter;
    private Subcategory subcategory;
    private List<Article> articles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategory);

        //Obtencion de datos
        Bundle b = getIntent().getExtras();
        subcategory = (Subcategory)b.getSerializable("subcategory");
        articles = subcategory.getArticleList();
        //End obtencion de datos
    }
}
