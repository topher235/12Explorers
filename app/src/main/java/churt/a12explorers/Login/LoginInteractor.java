package churt.a12explorers.Login;

/**
 * Created by Christopher Hurt on 9/8/2017.
 *
 * Interface of the login interactor.
 */

public interface LoginInteractor {

    interface LoginInteractorListener {

        void onEmailError();

        void onPasswordError();

        void onSuccess();

        void onAuthError();
    }

    void signInUser(String email, String password, LoginInteractorListener listener);
}
