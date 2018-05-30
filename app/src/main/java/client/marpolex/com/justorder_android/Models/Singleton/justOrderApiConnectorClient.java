package client.marpolex.com.justorder_android.Models.Singleton;

import java.net.MalformedURLException;
import client.marpolex.com.justorder_android.API.justOrderApiConnector;

public class justOrderApiConnectorClient {
    private static justOrderApiConnector apiConnector = null;

    private justOrderApiConnectorClient(){}

    public static justOrderApiConnector getJustOrderApiConnector() {
        if (apiConnector == null) {
            try {
                apiConnector = new justOrderApiConnector();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return apiConnector;
    }

    public static void resetJustOrderApiConnector() {
        apiConnector = null;
    }
}
