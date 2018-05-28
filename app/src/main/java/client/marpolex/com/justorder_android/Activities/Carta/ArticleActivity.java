package client.marpolex.com.justorder_android.Activities.Carta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import client.marpolex.com.justorder_android.API.justOrderApiInterface;
import client.marpolex.com.justorder_android.Activities.LoginActivity;
import client.marpolex.com.justorder_android.Adapters.ArticlesAdapter;
import client.marpolex.com.justorder_android.Adapters.RatingsAdapter;
import client.marpolex.com.justorder_android.Models.Article;
import client.marpolex.com.justorder_android.Models.Singleton.ShoppingCart;
import client.marpolex.com.justorder_android.Models.Singleton.ShoppingCartClient;
import client.marpolex.com.justorder_android.Models.Singleton.justOrderApiConnectorClient;
import client.marpolex.com.justorder_android.Models.User;
import client.marpolex.com.justorder_android.R;

public class ArticleActivity extends AppCompatActivity implements justOrderApiInterface {

    private Toolbar toolbar;
    private Article article;
    private Button btnSendReview;
    private RatingBar ratingBarUser;
    private EditText edtReview;
    ProgressDialog dialogLoding;
    //private ShoppingCart shoppingCart = ShoppingCartClient.getShoppingCart();
    private RecyclerView recyclerView;
    private RatingsAdapter rAdapter;


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
        loadRatings();

        //Elementos enviar comentarios
        ratingBarUser = findViewById(R.id.ratingBarUser);
        edtReview = findViewById(R.id.edtReview);
        btnSendReview = findViewById(R.id.btnSendReview);
        btnSendReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRating();
            }
        });
    }

    private void sendRating() {
        lockInterface();
        int restaurantId = ShoppingCartClient.getShoppingCart().getRestaurantId();
        int articleId = (int) article.getId();
        String comment = edtReview.getText().toString();
        float rating = ratingBarUser.getRating();

        justOrderApiConnectorClient.getJustOrderApiConnector().sendRatingProduct(restaurantId, articleId, comment, rating, this);
    }

    private void lockInterface() {
        dialogLoding = ProgressDialog.show(this, "", "Cargando, por favor espere...", true);
        dialogLoding.show();
    }

    private void unLockInterface() {
        dialogLoding.hide();
    }

    private void loadArticleInfo() {
        TextView name = (TextView) findViewById(R.id.tvName);
        TextView description = (TextView) findViewById(R.id.tvDescription);
        ImageView imageView = (ImageView) findViewById(R.id.ivRestaurant);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        name.setText(article.getName());
        description.setText(article.getDescription());
        ratingBar.setRating((float) article.getRating());

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);

        //Para que la imagen encaje perfecto
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, r.getDisplayMetrics());
        Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);

        if(! article.getImage().isEmpty())
            Picasso.get().load(article.getImage()).placeholder(R.drawable.logo).resize(screenSize.x, (int)px).into(imageView); //.centerCrop()

        ratingBar.setRating((float) article.getRating());
    }
    private void loadRatings(){
        //Recycler view
        recyclerView = findViewById(R.id.recyclerView);
        rAdapter = new RatingsAdapter(article.getRatingsList());
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(rLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(rAdapter);
        //End Recycler view
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

    @Override
    public void sendRatingProduct_response(String jsonResponse) {
        try {
            JSONObject response = new JSONObject(jsonResponse);
            boolean success = response.getBoolean("success");
            if (!success) {             //Comment failed
                Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();
                unLockInterface();
            } else {                    //Username and password OK
                justOrderApiConnectorClient.getJustOrderApiConnector().clearCallbackActivity();
                Toast.makeText(this, "Comentario enviado correctamente.", Toast.LENGTH_SHORT).show();
                unLockInterface();
            }
        } catch (JSONException e) {     //JSON couldn't be parsed or no connection to api server
            Log.d("attemptLogin", e.toString());
            Toast.makeText(this, "Error al conectar con la API", Toast.LENGTH_SHORT).show();
            unLockInterface();
        }
    }



    @Override
    public void attemptLogin_response(String jsonResponse) {

    }

    @Override
    public void attemptRegister_response(String jsonResponse) {

    }

    @Override
    public void attemptOrder_response(String jsonResponse) {

    }

    @Override
    public void getRestaurants_response(String jsonResponse) {

    }

    @Override
    public void getCatalog_response(String jsonResponse) {

    }

    @Override
    public void attemptGetTable_response(String jsonResponse) {

    }

    @Override
    public void attemptPay_response(String jsonResponse) {

    }

    @Override
    public void sendRatingRestaurant_response(String jsonResponse) {

    }
}
