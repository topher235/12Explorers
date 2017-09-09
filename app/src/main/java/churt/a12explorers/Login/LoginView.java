package churt.a12explorers.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Christopher Hurt on 9/8/2017.
 *
 * Interface for the login view.
 */

public interface LoginView {

    void setEmailError();

    void setPasswordError();

    void setAuthError();

    void navigateToHome();

}