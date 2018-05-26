package client.marpolex.com.justorder_android.Activities.Carta;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.security.AllPermission;
import java.util.ArrayList;
import java.util.List;

import client.marpolex.com.justorder_android.Adapters.ArticlesAdapter;
import client.marpolex.com.justorder_android.Models.Article;
import client.marpolex.com.justorder_android.Models.Singleton.ShoppingCart;
import client.marpolex.com.justorder_android.Models.Singleton.ShoppingCartClient;
import client.marpolex.com.justorder_android.Models.Subcategory;
import client.marpolex.com.justorder_android.R;

import static android.content.ContentValues.TAG;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArticlesAdapter aAdapter;
    private ArrayList<Article> allArticles, articles;
    private Toolbar toolbar;
    private EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Obtencion de datos
        allArticles = ShoppingCartClient.getShoppingCart().getAllArticles();
        articles = new ArrayList<>();
        articles.addAll(allArticles);
        //End obtencion de datos

        //TOOLBAR
        toolbar = (Toolbar) findViewById(R.id.tool_bar);  // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar); // Setting toolbar as the ActionBar with setSupportActionBar() call
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        //getSupportActionBar().setTitle(subcategory.getName());
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

                Intent i = new Intent(SearchActivity.this, ArticleActivity.class);

                Article article = articles.get((int) idArticle);
                Bundle args = new Bundle();
                args.putSerializable("article", article);
                i.putExtras(args);

                startActivity(i);
            }
        });
        //End Recycler view

        edtSearch = findViewById(R.id.edtSearch);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getAllArticlesFilter(edtSearch.getText().toString());
                //Log.d("onTextChanged", articles.size()+"");
                aAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void getAllArticlesFilter(String name) {
        articles.clear();

        //Log.d(TAG, "getAllArticlesFilter: "+name);
        if(name == ""){
            Log.d(TAG, "getAllArticlesFilter: returneo aqui");
            articles.addAll(allArticles);
            return;
        }

        Log.d(TAG, "getAllArticlesFilter: "+allArticles.hashCode());
        Log.d(TAG, "getAllArticlesFilter: "+allArticles.size());

        for(Article article : allArticles){
            Log.d("for Articles", article.getName().toLowerCase()+" - "+name.toLowerCase());
            if(article.getName().toLowerCase().contains(name.toLowerCase())) articles.add(article);
        }

        Log.d("filtedArticles", articles.size()+"");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar_search, menu);
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
            Intent intent = new Intent(SearchActivity.this, cartSummaryActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
