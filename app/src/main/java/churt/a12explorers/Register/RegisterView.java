package churt.a12explorers.Register;

/**
 * Created by Christopher Hurt on 9/8/2017.
 *
 * Interface for the register view.
 */

public interface RegisterView {
    void navigateToLogin();

    void navigateToHome();

    void setEmailError();

    void setPasswordError();

    void setConfirmPassError();

    void setAuthError();
}
