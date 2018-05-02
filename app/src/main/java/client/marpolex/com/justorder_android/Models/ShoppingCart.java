package client.marpolex.com.justorder_android.Models;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    //Map articulo cantidad
    private Map<Article, Integer> shoppingMap;

    ShoppingCart() {
        shoppingMap = new HashMap<>();
    }

    public void addArticle(Article article) {
        if (!shoppingMap.containsKey(article)) {
            shoppingMap.put(article, 1);
        } else {
            int quantity = shoppingMap.get(article);
            quantity++;
            shoppingMap.put(article, quantity);
        }
    }

    public int getQuantity(Article article) {
        return shoppingMap.get(article);
    }

    public void setQuantity(Article article, int quantity) {
        if (quantity > 0) shoppingMap.put(article, quantity);
        else Log.d("ShoppingMap", "Quantity not correct");
    }
}
