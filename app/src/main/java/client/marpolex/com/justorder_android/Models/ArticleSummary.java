package client.marpolex.com.justorder_android.Models;

import java.io.Serializable;

public class ArticleSummary implements Serializable {
    public long id;
    public int quantity;

    public ArticleSummary(long id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }
}
