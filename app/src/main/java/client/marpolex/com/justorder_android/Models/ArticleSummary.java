package client.marpolex.com.justorder_android.Models;

import java.io.Serializable;

public class ArticleSummary implements Serializable {
    public long product_id;
    public int quantity;
    public boolean isSelectedToPay = false;

    public ArticleSummary(long product_id, int quantity) {
        this.product_id = product_id;
        this.quantity = quantity;
    }
}
