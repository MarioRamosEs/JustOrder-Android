package client.marpolex.com.justorder_android.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.orm.SugarContext;

import client.marpolex.com.justorder_android.API.justOrderApiConnector;
import client.marpolex.com.justorder_android.API.justOrderApiInterface;
import client.marpolex.com.justorder_android.Models.Rating;
import client.marpolex.com.justorder_android.Models.Singleton.justOrderApiConnectorClient;
import client.marpolex.com.justorder_android.R;

public class RegisterActivity extends AppCompatActivity implements OnClickListener, justOrderApiInterface {
    ProgressDialog dialogLoding;
    private static justOrderApiConnector apiConnector;


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
    }

    @Override
    public void onClick(View view) {
        /*switch (view.getId()) {
            case R.id.btn_Test:
                mEmailView.setText("mario.ramos@mataro.epiaedu.cat");
                mPasswordView.setText("123456");
                break;
            case R.id.email_sign_in_button:
                attemptLogin();
                break;
        }*/
    }

    public void attemptRegister(){
        dialogLoding.show();

        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);
        EditText name = (EditText) findViewById(R.id.edtName);
        EditText surname = (EditText) findViewById(R.id.edtSurnames);
        EditText birthDate = (EditText) findViewById(R.id.edtBirthDate);

        apiConnector.attemptRegister(email.getText().toString(), password.getText().toString(), name.getText().toString(), surname.getText().toString(), birthDate.getText().toString(), getGender(), this);
    }

    public int getGender(){
        RadioButton rb1 = (RadioButton) findViewById(R.id.rbGender1);
        RadioButton rb2 = (RadioButton) findViewById(R.id.rbGender2);
        RadioButton rb3 = (RadioButton) findViewById(R.id.rbGender3);

        if(rb1.isChecked()) return 1;
        if(rb2.isChecked()) return 2;
        if(rb3.isChecked()) return 3;
        return 1;
    }

    @Override
    public void attemptLogin_response(String jsonResponse) {
        dialogLoding.hide();

        //TODO mirar si la respuesta es buena o no. Mostrar mensaje. Cerrar la activity
    }

    @Override
    public void attemptRegister_response(String jsonResponse) {
        Log.d("attemptRegister", "attemptRegister_response: " + jsonResponse);
        Intent i = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(i);
    }

    /*
    private void attemptLogin() {
        lockInterface();
        this.apiConnector.attemptLogin(this.mEmailView.getText().toString(), this.mPasswordView.getText().toString(), this);
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
    */
}

