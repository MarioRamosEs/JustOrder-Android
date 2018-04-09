package client.marpolex.com.justorder_android.Activities;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import client.marpolex.com.justorder_android.Models.Article;
import client.marpolex.com.justorder_android.Models.Subcategory;
import client.marpolex.com.justorder_android.R;

public class ArticleActivity extends AppCompatActivity {

    private Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        //Obtencion de datos
        Bundle b = getIntent().getExtras();
        article = (Article) b.getSerializable("article");
        //End obtencion de datos

        loadArticleInfo();
    }

    private void loadArticleInfo() {
        TextView name = (TextView) findViewById(R.id.tvName);
        TextView description = (TextView) findViewById(R.id.tvDescription);
        ImageView imageView = (ImageView)  findViewById(R.id.ivRestaurant);

        name.setText(article.getName());
        description.setText(article.getDescription());

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        Picasso.get().load(article.getImg()).placeholder(R.drawable.logo).into(imageView); //.centerCrop()
    }
}
