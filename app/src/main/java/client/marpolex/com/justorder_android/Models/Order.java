package client.marpolex.com.justorder_android.Models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Order implements Serializable {

    private int orderId;
    private int productId;
    private int paid;
    private String status;
    private int quantity;
    private String userJustOrder;
    private boolean isWaiter;
    private Article product;
    public boolean isSelectedToPay = true;

    public Order(JSONObject jsonObject) {
        try {
            orderId = jsonObject.getInt("id");
            productId = jsonObject.getInt("id_product");
            paid = jsonObject.getInt("paid");
            status = jsonObject.getString("status");
            quantity = jsonObject.getInt("quantity");
            isWaiter = jsonObject.getBoolean("is_waiter");

            if(paid == 1) isSelectedToPay = false;

            if (jsonObject.isNull("user_justorder"))
                userJustOrder = "";
            else
                userJustOrder = jsonObject.getJSONObject("user_justorder").getString("name") + " " + jsonObject.getJSONObject("user_justorder").getString("surnames");

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

    public void changeIsSelectedToPay(){
        if(paid == 1){
            isSelectedToPay = false;
            return;
        }
        isSelectedToPay = !isSelectedToPay;
    }

    public String getPaid() {
        String response = "-";
        switch (paid) {
            case 0:
                response = "Pendiente de pago";
                break;
            case 1:
                response = "Pagado";
                break;
        }
        return response;
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

    public String getOrderedBy() {
        if (isWaiter) {
            if (userJustOrder.isEmpty()) return "TPV";
            else return "Camarero";
        } else return userJustOrder;
    }
}
