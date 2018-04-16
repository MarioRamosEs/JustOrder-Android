package client.marpolex.com.justorder_android.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import client.marpolex.com.justorder_android.Adapters.ArticlesAdapter;
import client.marpolex.com.justorder_android.Adapters.SubcategoriesAdapter;
import client.marpolex.com.justorder_android.Models.Article;
import client.marpolex.com.justorder_android.Models.Category;
import client.marpolex.com.justorder_android.Models.ShoppingCart;
import client.marpolex.com.justorder_android.Models.ShoppingCartClient;
import client.marpolex.com.justorder_android.Models.Subcategory;
import client.marpolex.com.justorder_android.R;

public class SubcategoryActivity extends AppCompatActivity {

    private ShoppingCart shoppingCart = ShoppingCartClient.getShoppingCart();

    private RecyclerView recyclerView;
    private ArticlesAdapter aAdapter;
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

        //Recycler view
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        aAdapter = new ArticlesAdapter(articles);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(rLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(aAdapter);

        aAdapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long idArticle = aAdapter.getItemId(recyclerView.getChildAdapterPosition(v));

                Intent i = new Intent(SubcategoryActivity.this, ArticleActivity.class);

                Article article = articles.get((int) idArticle);
                Bundle args = new Bundle();
                args.putSerializable("article", article);
                i.putExtras(args);

                startActivity(i);
            }
        });
        //End Recycler view
    }
}
