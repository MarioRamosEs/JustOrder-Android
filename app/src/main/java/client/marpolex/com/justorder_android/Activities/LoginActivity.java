package client.marpolex.com.justorder_android.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.orm.SugarContext;

import client.marpolex.com.justorder_android.Models.User;
import client.marpolex.com.justorder_android.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener {
    ProgressDialog dialogLoding;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SugarContext.init(this);

        dialogLoding = ProgressDialog.show(LoginActivity.this, "", "Cargando, por favor espere...", true);
        dialogLoding.hide();

        //Listener botones
        findViewById(R.id.btn_Test).setOnClickListener(this);
        findViewById(R.id.email_sign_in_button).setOnClickListener(this);

        if (User.listAll(User.class).size() > 0) { //Si hay un usuario en la BDD pasa directamente a MainActivity
            goToMainActivity();
        }

        //mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        //mPasswordView = (EditText) findViewById(R.id.password);
        //mLoginFormView = findViewById(R.id.login_form);
        //mProgressView = findViewById(R.id.login_progress);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Test:
                User user = new User("Mario", "Ramos", 100, 1, 20, "tempToken");
                user.save();
                LoginActivity.this.goToMainActivity();
                break;
            case R.id.email_sign_in_button:
                Log.d("debug", "onClick: email_sign_in_button");
                break;
        }
    }

    public void goToMainActivity() {
        dialogLoding.show();
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivityForResult(i, 0001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 0001) { //Si volvemos de la main activity porque han cerrado la sesión
            dialogLoding.hide();
        }
    }
}

