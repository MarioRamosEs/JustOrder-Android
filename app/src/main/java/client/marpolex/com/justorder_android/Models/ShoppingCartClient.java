package client.marpolex.com.justorder_android.Models;

public class ShoppingCartClient {
    private static ShoppingCart shoppingCart = null;

    public static ShoppingCart getShoppingCart() {
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
        }
        return shoppingCart;
    }

    public static void resetShoppingCart() {
        shoppingCart = null;
    }
}
