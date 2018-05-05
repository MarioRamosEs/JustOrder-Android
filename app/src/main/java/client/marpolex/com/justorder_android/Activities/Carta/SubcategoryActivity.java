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

import client.marpolex.com.justorder_android.Adapters.ArticlesAdapter;
import client.marpolex.com.justorder_android.Models.Article;
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
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategory);

        //Obtencion de datos
        Bundle b = getIntent().getExtras();
        subcategory = (Subcategory) b.getSerializable("subcategory");
        articles = subcategory.getArticleList();
        //End obtencion de datos

        //TOOLBAR
        toolbar = (Toolbar) findViewById(R.id.tool_bar);  // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar); // Setting toolbar as the ActionBar with setSupportActionBar() call
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle(subcategory.getName());
        //END TOOLBAR

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

                Article article = articles.get((int) idArticle - 1);
                Bundle args = new Bundle();
                args.putSerializable("article", article);
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
            Intent intent = new Intent(SubcategoryActivity.this, cartSummaryActivity.class);
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
}
