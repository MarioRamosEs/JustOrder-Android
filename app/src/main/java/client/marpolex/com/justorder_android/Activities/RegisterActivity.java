package client.marpolex.com.justorder_android.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.orm.SugarContext;

import org.json.JSONException;
import org.json.JSONObject;

import client.marpolex.com.justorder_android.API.justOrderApiConnector;
import client.marpolex.com.justorder_android.API.justOrderApiInterface;
import client.marpolex.com.justorder_android.Models.Singleton.justOrderApiConnectorClient;
import client.marpolex.com.justorder_android.R;

public class RegisterActivity extends AppCompatActivity implements OnClickListener, justOrderApiInterface {
    private static justOrderApiConnector apiConnector;
    private ProgressDialog dialogLoding;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        SugarContext.init(this);

        //Instanciate a new apiConnector
        apiConnector = justOrderApiConnectorClient.getJustOrderApiConnector();

        dialogLoding = ProgressDialog.show(RegisterActivity.this, "", "Cargando, por favor espere...", true);
        dialogLoding.hide();

        //Auto check primer genero
        RadioButton rbGender1 = (RadioButton) findViewById(R.id.rbGender1);
        rbGender1.toggle();

        registerButton = findViewById(R.id.btn_Register);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Register:
                attemptRegister();
                break;
        }
    }

    @Override
    public void attemptLogin_response(String jsonResponse) {
    }

    @Override
    public void attemptRegister_response(String jsonResponse) {
        Log.d("attemptRegister", "attemptRegister_response: " + jsonResponse);
        dialogLoding.hide();

        try {
            JSONObject response = new JSONObject(jsonResponse);
            boolean success = response.getBoolean("success");

            if (success) {
                Toast.makeText(this.getApplicationContext(), "Usuario creado correctamente.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this.getApplicationContext(), "Error al crear el usuario.", Toast.LENGTH_SHORT).show();
                unLockInterface();
            }

        } catch (JSONException e) {
            Log.d("attemptLogin", e.toString());
            Toast.makeText(this.getApplicationContext(), "Error al conectar con la API", Toast.LENGTH_SHORT).show();
            unLockInterface();
        }
    }

    @Override
    public void getRestaurants_response(String jsonResponse) {

    }

    public void attemptRegister() {
        lockInterface();
        dialogLoding.show();

        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        EditText name = findViewById(R.id.edtName);
        EditText surname = findViewById(R.id.edtSurnames);
        EditText birthDate = findViewById(R.id.edtBirthDate);

        apiConnector.attemptRegister(email.getText().toString(), password.getText().toString(), name.getText().toString(), surname.getText().toString(), birthDate.getText().toString(), getGender(), this);
    }

    public int getGender() {
        RadioButton rb1 = findViewById(R.id.rbGender1);
        RadioButton rb2 = findViewById(R.id.rbGender2);
        RadioButton rb3 = findViewById(R.id.rbGender3);

        if (rb1.isChecked()) return 1;
        if (rb2.isChecked()) return 2;
        if (rb3.isChecked()) return 3;
        return 1;
    }

    private void unLockInterface() {
        registerButton.setEnabled(true);
    }

    private void lockInterface() {
        registerButton.setEnabled(false);
    }
}

