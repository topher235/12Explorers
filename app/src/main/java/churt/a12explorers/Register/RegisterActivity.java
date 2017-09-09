package churt.a12explorers.Register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import churt.a12explorers.R;

/**
 * Created by Christopher Hurt on 9/8/2017.
 *
 * Implementation of the register view.
 *
 * TODO: Create frontend
 */

public class RegisterActivity extends Activity implements RegisterView,
        View.OnClickListener {

    private FirebaseAuth mAuth;
    private final String TAG = this.getClass().getSimpleName();
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText inputConfirmPass;
    private TextView unhidePass;
    private Button button;
    private RegisterPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get UI Components
        inputEmail = (EditText) findViewById(R.id.registerEmailInput);
        inputPassword = (EditText) findViewById(R.id.registerPasswordInput);
        inputConfirmPass = (EditText) findViewById(R.id.registerPasswordConfirm);
        unhidePass = (TextView) findViewById(R.id.registerUnhidePassword);
        button = (Button) findViewById(R.id.registerBtn);

        // Get FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // Set onClick Listeners
        button.setOnClickListener(this);

        // Initialize presenter
        presenter = new RegisterPresenterImpl(this);

    }

    // **** REGISTER USER METHODS **** //

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerBtn:
                String email = inputEmail.getText().toString().trim();
                String pass = inputPassword.getText().toString().trim();
                String confirmPass = inputConfirmPass.getText().toString().trim();
                presenter.registerUser(email, pass, confirmPass);
            case R.id.registerUnhidePassword:
                presenter.changePassVisibility(inputPassword, unhidePass);
        }
    }

    public void unhidePass() {
        if(unhidePass.getText().equals("unhide")) {
            inputPassword.setTransformationMethod(null);
            unhidePass.setText("hide");
        } else {
            inputPassword.setTransformationMethod(new PasswordTransformationMethod());
            unhidePass.setText("unhide");
        }
    }

    @Override
    public void navigateToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void navigateToHome() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void setEmailError() {
        Toast.makeText(this, R.string.email_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPasswordError() {
        Toast.makeText(this, R.string.password_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setConfirmPassError() {
        Toast.makeText(this, R.string.confirm_password_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setAuthError() {
        Toast.makeText(this, R.string.auth_failed, Toast.LENGTH_SHORT).show();
    }
}
