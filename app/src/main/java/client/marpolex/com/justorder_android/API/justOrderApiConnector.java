package client.marpolex.com.justorder_android.API;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class justOrderApiConnector extends AsyncTask<String, Void, JSONObject> {
    private URL apiUrl;
    private final String baseUrl = "https://webapp.justorder.ovh";
    private String token = "";
    private justOrderApiInterface callBackActivity;

    public justOrderApiConnector() throws MalformedURLException {
    }

    public void attemptLogin(String username, String password, justOrderApiInterface activity) {
        this.callBackActivity = activity;
        this.doInBackground("login", username, password);
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        JSONObject request = new JSONObject();

        HttpURLConnection conn = null;
        BufferedReader reader = null;
        try {
            switch (params[0]) {
                /*case "status":
                case "statusWithRequestCode":
                    request.put("query", "status");
                    request.put("token", this.token);
                    break;*/
                case "login":
                    request.put("email", params[1]);
                    request.put("password", params[2]);

                    apiUrl = new URL(baseUrl + "/api/login");
                    //cambiar a post
                    break;
                case "sites":
                     apiUrl = new URL(baseUrl + "/api/sites");
                     //cambiar a get
                    break;
            }

            //STRING METHOD

            conn = (HttpURLConnection) this.apiUrl.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("JWT-TOKEN", (token == null) ? "" : token);

            //SEND
            Writer writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            writer.write(request.toString());
            writer.close();
            //END SEND

            //RECEIVE
            InputStream inputStream;
            int status = conn.getResponseCode();
            Log.d(TAG, "ResponseCode: " + status);

            if (status != HttpURLConnection.HTTP_OK) inputStream = conn.getErrorStream();
            else inputStream = conn.getInputStream();

            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                Log.w("api_response", "Empty response");
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String inputLine;
            while ((inputLine = reader.readLine()) != null)
                buffer.append(inputLine + "\n");

            if (buffer.length() == 0) {
                // Stream was empty. No point in parsing.
                Log.w("api_response", "Empty response");
                return null;
            }
            if (callBackActivity == null) {
                Log.w("api_response", "No callback defined");
                return null;
            }
            //END RECEIVE

            switch (params[0]) {
                case "status":
                    //this.callBackActivity.getStatus_response(buffer.toString(), 0);
                    break;
                case "login":
                    callBackActivity.attemptLogin_response(buffer.toString());
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //CLOSE CONNECTIONS
            if (conn != null) {
                conn.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("ERROR", "Error closing stream", e);
                }
            }
        }

        return null;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void clearCallbackActivity() {
        this.callBackActivity = null;
    }
}
