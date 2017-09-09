package churt.a12explorers.Login;

import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Christopher Hurt on 9/8/2017.
 *
 * Implementation of the login presenter.
 */

public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.LoginInteractorListener {

    private LoginInteractorImpl interactor;
    private LoginView loginView;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        interactor = new LoginInteractorImpl();
    }

    @Override
    public void validateCredentials(String email, String password) {
        interactor.signInUser(email, password, this);
    }

    @Override
    public void changePassVisibility(EditText pass, TextView unhideText) {
        if(unhideText.getText().equals("unhide")) {
            pass.setTransformationMethod(null);
            unhideText.setText("hide");
        } else {
            pass.setTransformationMethod(new PasswordTransformationMethod());
            unhideText.setText("unhide");
        }
    }

    // ***** LISTENER METHODS ***** //

    @Override
    public void onEmailError() {
        loginView.setEmailError();
    }

    @Override
    public void onPasswordError() {
        loginView.setPasswordError();
    }

    @Override
    public void onSuccess() {
        loginView.navigateToHome();
    }

    @Override
    public void onAuthError() {
        loginView.setAuthError();
    }
}
