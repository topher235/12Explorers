package churt.a12explorers.Account;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import churt.a12explorers.R;

/**
 * Created by Christopher Hurt on 9/12/2017.
 */

public class AccountTab extends Fragment {
    private Button signOutBtn;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        mAuth = FirebaseAuth.getInstance();

        signOutBtn = (Button) rootView.findViewById(R.id.account_sign_out_btn);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
            }
        });

        return rootView;
    }
}
