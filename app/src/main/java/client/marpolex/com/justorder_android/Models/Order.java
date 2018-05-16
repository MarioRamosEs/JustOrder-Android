package client.marpolex.com.justorder_android.Models;

import org.json.JSONException;
import org.json.JSONObject;

public class Order {

    private int orderId;
    private int productId;
    private int paid;
    private String status;
    private int quantity;
    private int userJustOrder;
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
                userJustOrder = -1;
            else
                userJustOrder = jsonObject.getInt("user_justorder");

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

    public int getUserJustOrder() {
        return userJustOrder;
    }

    public boolean isWaiter() {
        return isWaiter;
    }

    public Article getProduct() {
        return product;
    }
}
