package churt.a12explorers.Register;

import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Christopher Hurt on 9/8/2017.
 *
 * Implementation of the register presenter.
 */

public class RegisterPresenterImpl implements RegisterPresenter,
        RegisterInteractor.RegisterInteractorListener {

    private RegisterView registerView;
    private RegisterInteractor registerInteractor;

    public RegisterPresenterImpl(RegisterView view) {
        this.registerView = view;
        this.registerInteractor = new RegisterInteractorImpl();
    }

    @Override
    public void registerUser(String email, String pass, String confirmPass) {
        registerInteractor.insertUserIntoDatabase(email, pass, confirmPass, this);
    }

    @Override
    public void changePassVisibility(EditText editText, TextView textView) {
        if(textView.getText().equals("unhide")) {
            editText.setTransformationMethod(null);
            textView.setText("hide");
        } else {
            editText.setTransformationMethod(new PasswordTransformationMethod());
            textView.setText("unhide");
        }
    }

    // ***** LISTENER METHODS ***** //

    @Override
    public void onEmailError() {
        registerView.setEmailError();
    }

    @Override
    public void onPasswordError() {
        registerView.setPasswordError();
    }

    @Override
    public void onConfirmPasswordError() {
        registerView.setConfirmPassError();
    }

    @Override
    public void onSuccess() {
        registerView.navigateToHome();
    }

    @Override
    public void onAuthError() {
        registerView.setAuthError();
    }
}
