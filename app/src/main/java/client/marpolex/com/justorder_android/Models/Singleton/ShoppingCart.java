package client.marpolex.com.justorder_android.Models.Singleton;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.marpolex.com.justorder_android.Models.Article;
import client.marpolex.com.justorder_android.Models.ArticleSummary;

import static android.content.ContentValues.TAG;

public class ShoppingCart {
    //Map articulo cantidad
    private Map<Article, Integer> shoppingMap;
    private ArrayList<Article> allArticles;
    private int restaurantId, tableId;

    ShoppingCart() {
        shoppingMap = new HashMap<>();
        allArticles = new ArrayList<>();
    }

    public void addArticleToTotalArticles(Article article){
        for (Article articleTemp : allArticles) {
            if (article.getId() == articleTemp.getId()) { //Si encuentra otro igual sale de la función
                return;
            }
        }
        allArticles.add(article);
    }

    public ArrayList<Article> getAllArticles() {
        Log.d(TAG, "getAllArticles: "+allArticles.hashCode());
        Log.d(TAG, "getAllArticles: "+allArticles.size());
        return allArticles;
    }

    public void addArticle(Article article) {
        //Miro si hay un articulo con el mismo id que el que me pasan.
        Object[] obj = shoppingMap.keySet().toArray();
        for (Object o : obj) {
            Article articleTemp = (Article) o;
            if (article.getId() == articleTemp.getId()) { //Si lo hay, actualizo cantidades
                int quantity = shoppingMap.get(articleTemp);
                quantity++;
                shoppingMap.put(articleTemp, quantity);
                Log.d(TAG, "Articulo " + articleTemp.getName() + " añadido. Cantidad: " + getQuantity(articleTemp));
                return;
            }
        }

        //Si llego hasta aqui es que no existe, entonces añado el objeto como tal
        shoppingMap.put(article, 1);
        Log.d(TAG, "Articulo " + article.getName() + " añadido. Cantidad: " + getQuantity(article));
    }

    public void removeArticle(Article article){
        //Miro si hay un articulo con el mismo id que el que me pasan.
        Object[] obj = shoppingMap.keySet().toArray();
        for (Object o : obj) {
            Article articleTemp = (Article) o;
            if (article.getId() == articleTemp.getId()) { //Si lo hay, lo borro
                shoppingMap.remove(o);
                Log.d(TAG, "Articulo " + article.getName() + " borrado");
                return;
            }
        }
    }

    public int getQuantity(Article article) {
        return shoppingMap.get(article);
    }

    public void setQuantity(Article article, int quantity) {
        if (quantity > 0) shoppingMap.put(article, quantity);
        else Log.d("ShoppingMap", "Quantity not correct");
    }

    public Map<Article, Integer> getShoppingMap() {
        return shoppingMap;
    }

    public float getTotalPrice(){
        float totalPrice = 0;
        Object[] obj = shoppingMap.keySet().toArray();
        for (Object o : obj) {
            Article articleTemp = (Article) o;
            totalPrice += (articleTemp.getBase_price() * getQuantity(articleTemp));
        }
        return totalPrice;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public String cartSummaryToJson(){
        List<ArticleSummary> articleActivity = new ArrayList<>();

        Object[] obj = shoppingMap.keySet().toArray();
        for (Object o : obj) {
            Article articleTemp = (Article) o;
            articleActivity.add(new ArticleSummary(articleTemp.getId(), ShoppingCartClient.getShoppingCart().getQuantity(articleTemp)));
        }

        Type type = new TypeToken<List<ArticleSummary>>() {}.getType();
        Gson gson = new Gson();
        return gson.toJson(articleActivity, type);
    }

    /*
    private String generalInfoToJson(){
        String temp;

        temp =  " \"site_id\":\""   + ShoppingCartClient.getShoppingCart().getRestaurantId() + "\", ";
        temp += " \"table_id\":\""  + ShoppingCartClient.getShoppingCart().getTableId()      + "\", ";

        return temp;
    }*/
}
