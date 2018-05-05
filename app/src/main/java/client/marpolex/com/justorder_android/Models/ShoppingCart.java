package client.marpolex.com.justorder_android.Models;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class ShoppingCart {
    //Map articulo cantidad
    private Map<Article, Integer> shoppingMap;

    ShoppingCart() {
        shoppingMap = new HashMap<>();
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

    public int getQuantity(Article article) {
        return shoppingMap.get(article);
    }

    public void setQuantity(Article article, int quantity) {
        if (quantity > 0) shoppingMap.put(article, quantity);
        else Log.d("ShoppingMap", "Quantity not correct");
    }

}
