package churt.a12explorers.Register;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;

/**
 * Created by Christopher Hurt on 9/8/2017.
 *
 * Implementation of the register interactor.
 */

public class RegisterInteractorImpl implements RegisterInteractor {
    private FirebaseAuth mAuth;
    private final static String TAG = RegisterInteractorImpl.class.getSimpleName();

    @Override
    public void insertUserIntoDatabase(String email, String pass, String confirmPass, final RegisterInteractorListener listener) {
        this.mAuth = FirebaseAuth.getInstance();

        if(email.isEmpty()) {
            listener.onEmailError();
        } else if(pass.isEmpty()) {
            listener.onPasswordError();
        } else if(pass.equals(confirmPass)) {
            listener.onConfirmPasswordError();
        } else {
            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUserWithEmail:onComplete: " + task.isSuccessful());
                            if(task.isSuccessful()) {
                                listener.onSuccess();
                            } else {
                                listener.onAuthError();
                            }
                        }
                    });
        }
    }
}
