package client.marpolex.com.justorder_android.API;

public interface justOrderApiInterface {
    void attemptLogin_response(String jsonResponse);
    void attemptRegister_response(String jsonResponse);
    void attemptOrder_response(String jsonResponse);
    void getRestaurants_response(String jsonResponse);
    void getCatalog_response(String jsonResponse);
    void attemptGetTable_response(String jsonResponse);
    void attemptPay_response(String jsonResponse);
    void sendRatingProduct_response(String jsonResponse);
    void sendRatingRestaurant_response(String jsonResponse);
}
