package client.marpolex.com.justorder_android.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.orm.SugarContext;

import client.marpolex.com.justorder_android.API.justOrderApiConnector;
import client.marpolex.com.justorder_android.API.justOrderApiInterface;
import client.marpolex.com.justorder_android.Models.Rating;
import client.marpolex.com.justorder_android.Models.Singleton.justOrderApiConnectorClient;
import client.marpolex.com.justorder_android.R;

public class RegisterActivity extends AppCompatActivity implements OnClickListener {
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

    public void goToMainActivity() {
        dialogLoding.show();
        //TODO hacer registro con la api
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

