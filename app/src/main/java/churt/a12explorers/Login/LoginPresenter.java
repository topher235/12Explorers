package churt.a12explorers.Login;

import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Christopher Hurt on 9/8/2017.
 *
 * Interface for the login presenter.
 */

public interface LoginPresenter {

    void validateCredentials(String email, String password);

    void changePassVisibility(EditText pass, TextView unhideText);


}
