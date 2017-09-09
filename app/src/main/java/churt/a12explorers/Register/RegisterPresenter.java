package churt.a12explorers.Register;

import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Christopher Hurt on 9/8/2017.
 *
 * Interface of the register presenter.
 */

public interface RegisterPresenter {

    void registerUser(String email, String pass, String confirmPass);

    void changePassVisibility(EditText editText, TextView textView);

}
