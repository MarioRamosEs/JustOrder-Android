package client.marpolex.com.justorder_android.Activities.Carta;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import client.marpolex.com.justorder_android.Models.Article;
import client.marpolex.com.justorder_android.Models.Singleton.ShoppingCart;
import client.marpolex.com.justorder_android.Models.Singleton.ShoppingCartClient;
import client.marpolex.com.justorder_android.R;

public class ArticleActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Article article;
    private ShoppingCart shoppingCart = ShoppingCartClient.getShoppingCart();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        //Obtencion de datos
        Bundle b = getIntent().getExtras();
        article = (Article) b.getSerializable("article");
        //End obtencion de datos

        //TOOLBAR
        toolbar = (Toolbar) findViewById(R.id.tool_bar);  // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar); // Setting toolbar as the ActionBar with setSupportActionBar() call
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle(article.getName());
        //END TOOLBAR

        loadArticleInfo();
    }

    private void loadArticleInfo() {
        TextView name = (TextView) findViewById(R.id.tvName);
        TextView description = (TextView) findViewById(R.id.tvDescription);
        ImageView imageView = (ImageView) findViewById(R.id.ivRestaurant);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        name.setText(article.getName());
        description.setText(article.getDescription());

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);

        if(! article.getImage().isEmpty())
            Picasso.get().load(article.getImage()).placeholder(R.drawable.logo).into(imageView); //.centerCrop()

        ratingBar.setRating((float) article.getRating());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.article_toolbar_menu, menu);
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
            Intent intent = new Intent(ArticleActivity.this, cartSummaryActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_share) {
            //Todo Share
            Log.d("TODO", "onOptionsItemSelected: Share TODO");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
