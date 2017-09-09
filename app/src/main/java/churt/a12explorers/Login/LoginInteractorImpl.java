package churt.a12explorers.Login;

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
 * Implementation of the login interactor.
 */

public class LoginInteractorImpl implements LoginInteractor {

    private FirebaseAuth mAuth;
    private final static String TAG = LoginInteractorImpl.class.getSimpleName();

    @Override
    public void signInUser(String email, String password, final LoginInteractorListener listener) {
        this.mAuth = FirebaseAuth.getInstance();

        if(email.isEmpty()) {
            listener.onEmailError();
            return;
        } else if(password.isEmpty()) {
            listener.onPasswordError();
            return;
        } else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                            if(task.isSuccessful()) {
                                listener.onSuccess();
                            } else {
                                Log.w(TAG, "signInWithEmail:failed", task.getException());
                                listener.onAuthError();
                            }
                        }
                    });
        }
    }


}
