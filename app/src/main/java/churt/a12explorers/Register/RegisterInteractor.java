package churt.a12explorers.Register;

/**
 * Created by Christopher Hurt on 9/8/2017.
 *
 * Interface for the register interactor.
 */

public interface RegisterInteractor {
    interface RegisterInteractorListener {
        void onEmailError();

        void onPasswordError();

        void onConfirmPasswordError();

        void onSuccess();

        void onAuthError();
    }

    void insertUserIntoDatabase(String email, String pass, String confirmPass, RegisterInteractorListener listener);
}
