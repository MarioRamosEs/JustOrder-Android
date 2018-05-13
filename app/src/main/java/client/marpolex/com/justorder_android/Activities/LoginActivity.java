package client.marpolex.com.justorder_android.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.orm.SugarContext;

import org.json.JSONException;
import org.json.JSONObject;

import client.marpolex.com.justorder_android.API.justOrderApiConnector;
import client.marpolex.com.justorder_android.API.justOrderApiInterface;
import client.marpolex.com.justorder_android.Models.Singleton.justOrderApiConnectorClient;
import client.marpolex.com.justorder_android.Models.User;
import client.marpolex.com.justorder_android.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener, justOrderApiInterface {
    ProgressDialog dialogLoding;
    private static justOrderApiConnector apiConnector;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Button email_sign_in_button;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SugarContext.init(this);

        //Instanciate a new apiConnector
        apiConnector = justOrderApiConnectorClient.getJustOrderApiConnector();

        dialogLoding = ProgressDialog.show(LoginActivity.this, "", "Cargando, por favor espere...", true);
        dialogLoding.hide();

        //Si hay un usuario en la BDD pasa directamente a MainActivity
        if (User.listAll(User.class).size() > 0) {
            goToMainActivity();
        }

        //-----------------------------------------------------------------
        //Listener botones
        findViewById(R.id.btn_Test).setOnClickListener(this);
        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
        findViewById(R.id.btn_Register).setOnClickListener(this);

        //INTERNET USE POLICY
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Bindear vistas
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        email_sign_in_button = (Button) findViewById(R.id.email_sign_in_button);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Test:
                mEmailView.setText("mario.ramos@mataro.epiaedu.cat");
                mPasswordView.setText("123456");
                break;
            case R.id.email_sign_in_button:
                attemptLogin();
                break;
            case R.id.btn_Register:
                goToRegister();
                break;
        }
    }

    private void goToRegister() {
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
    }

    public void goToMainActivity() {
        dialogLoding.show();
        User user = User.listAll(User.class).get(0); //Obtiene el primer usuario. Solo debería haber uno.
        Log.d("Token", user.getToken());
        apiConnector.setToken(user.getToken());
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivityForResult(i, 0001);
    }

    private void attemptLogin() {
        lockInterface();
        apiConnector.attemptLogin(this.mEmailView.getText().toString(), this.mPasswordView.getText().toString(), this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 0001) { //Si volvemos de la main activity porque han cerrado la sesión
            dialogLoding.hide();
        }
    }

    @Override
    public void attemptLogin_response(String jsonResponse) {
        Log.d("attemptLogin", "attemptLogin_response: " + jsonResponse);

        try {
            JSONObject response = new JSONObject(jsonResponse);
            boolean success = response.getBoolean("success");
            if (!success) {             //Login failed
                Toast.makeText(this.getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                unLockInterface();
            } else {                    //Username and password OK
                apiConnector.clearCallbackActivity();

                JSONObject userJson = response.getJSONObject("user");
                User user = new User(userJson.getString("name"), userJson.getString("surnames"), userJson.getInt("exp"), userJson.getInt("gender"), userJson.getString("birthdate"), response.getString("token"));
                user.save();

                Log.d("attemptLogin", "Login usuario " + user.getName());
                goToMainActivity();
            }
            Toast.makeText(this.getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {     //JSON couldn't be parsed or no connection to api server
            Log.d("attemptLogin", e.toString());
            Toast.makeText(this.getApplicationContext(), "Error al conectar con la API", Toast.LENGTH_SHORT).show();
            unLockInterface();
        }
    }

    @Override
    public void attemptRegister_response(String jsonResponse) {

    }

    @Override
    public void getRestaurants_response(String jsonResponse) {

    }

    private void lockInterface() {
        this.email_sign_in_button.setEnabled(false);
        this.mPasswordView.setEnabled(false);
        this.mEmailView.setEnabled(false);
    }

    private void unLockInterface() {
        this.email_sign_in_button.setEnabled(true);
        this.mPasswordView.setEnabled(true);
        this.mEmailView.setEnabled(true);
    }
}

