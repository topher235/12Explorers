package churt.a12explorers.Register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import churt.a12explorers.Helper.User;
import churt.a12explorers.Login.LoginActivity;
import churt.a12explorers.Main.MainActivity;
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
    private EditText inputFName, inputLName, inputEmail, inputPassword, inputConfirmPass;
    private TextView unhidePass, toLogin;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Get UI Components
        inputFName = (EditText) findViewById(R.id.registerFirstNameInput);
        inputLName = (EditText) findViewById(R.id.registerLastNameInput);
        inputEmail = (EditText) findViewById(R.id.registerEmailInput);
        inputPassword = (EditText) findViewById(R.id.registerPasswordInput);
        inputConfirmPass = (EditText) findViewById(R.id.registerPasswordConfirm);
        unhidePass = (TextView) findViewById(R.id.registerUnhidePassword);
        toLogin = (TextView) findViewById(R.id.registerNotAUser);
        button = (Button) findViewById(R.id.registerBtn);

        // Get FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // Set onClick Listeners
        button.setOnClickListener(this);
        unhidePass.setOnClickListener(this);
        toLogin.setOnClickListener(this);

    }

    // **** REGISTER USER METHODS **** //

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerBtn:
                String fName = inputFName.getText().toString().trim();
                String lName = inputLName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String pass = inputPassword.getText().toString().trim();
                String confirmPass = inputConfirmPass.getText().toString().trim();
                registerUser(fName, lName, email, pass, confirmPass);
                break;
            case R.id.registerUnhidePassword:
                unhidePass();
                break;
            case R.id.registerNotAUser:
                navigateToLogin();
                break;
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

    @Override
    public void setNameError() {
        Toast.makeText(this, R.string.name_error, Toast.LENGTH_SHORT).show();
    }

    public void registerUser(final String firstName, final String lastName, final String email, final String pass,
                                       final String confirmPass) {
        this.mAuth = FirebaseAuth.getInstance();

        if(firstName.isEmpty() || lastName.isEmpty()) {
            setNameError();
        } else if(email.isEmpty()) {
            setEmailError();
        } else if(pass.isEmpty()) {
            setPasswordError();
        } else if(!pass.equals(confirmPass)) {
            setConfirmPassError();
        } else {
            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUserWithEmail:onComplete: " + task.isSuccessful());
                            if(task.isSuccessful()) {
                                insertIntoDatabase(firstName, lastName, email);
                                navigateToHome();
                            } else {
                                setAuthError();
                            }
                        }
                    });
        }
    }

    private void insertIntoDatabase(String firstName, String lastName, String email) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference myRef = db.getReference();
        User user = new User(firstName, lastName, email, "", 0);
        myRef.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(user);
    }
}
