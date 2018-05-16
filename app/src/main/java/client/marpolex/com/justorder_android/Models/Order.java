package client.marpolex.com.justorder_android.Models;

import org.json.JSONException;
import org.json.JSONObject;

public class Order {

    private int orderId;
    private int productId;
    private int paid;
    private String status;
    private int quantity;
    private String userJustOrder;
    private boolean isWaiter;
    private Article product;

    public Order(JSONObject jsonObject) {
        try {
            orderId = jsonObject.getInt("id");
            productId = jsonObject.getInt("id_product");
            paid = jsonObject.getInt("paid");
            status = jsonObject.getString("status");
            quantity = jsonObject.getInt("quantity");
            isWaiter = jsonObject.getBoolean("is_waiter");

            if (jsonObject.isNull("user_justorder"))
                userJustOrder = "";
            else
                userJustOrder = jsonObject.getJSONObject("user_justorder").getString("name")+" "+jsonObject.getJSONObject("user_justorder").getString("surnames");

            product = new Article(jsonObject.getJSONObject("product"), -1);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getOrderId() {
        return orderId;
    }

    public int getProductId() {
        return productId;
    }

    public int getPaid() {
        return paid;
    }

    public String getStatus() {
        return status;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isWaiter() {
        return isWaiter;
    }

    public Article getProduct() {
        return product;
    }

    public String getOrderedBy(){
        if (isWaiter){
            if(userJustOrder.isEmpty()) return "TPV";
            else return "Camarero";
        }
        else return userJustOrder;
    }
}
