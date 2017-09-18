package churt.a12explorers.Account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import churt.a12explorers.AvatarChoice;
import churt.a12explorers.Helper.User;
import churt.a12explorers.Main.MainActivity;
import churt.a12explorers.R;

/**
 * Created by Christopher Hurt on 9/12/2017.
 */

public class AccountTab extends Fragment {
    private Button signOutBtn;
    private TextView points, userName;
    private ImageButton avatarChoiceBtn;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        if(mAuth.getCurrentUser() != null) {
            signOutBtn = (Button) rootView.findViewById(R.id.account_sign_out_btn);
            signOutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mAuth.signOut();
                }
            });

            avatarChoiceBtn = (ImageButton) rootView.findViewById(R.id.account_avatar_imgBtn);
            setAvatarInUI();
            avatarChoiceBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivityForResult(new Intent(getActivity(), AvatarChoice.class), 1);
                }
            });

            points = (TextView) rootView.findViewById(R.id.account_num_points_textview);
            userName = (TextView) rootView.findViewById(R.id.account_name_user);
            setUserData();

        }
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1) {
            if(Activity.RESULT_OK == resultCode) {
                String result = data.getStringExtra("result");
                updateDB(result);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void updateDB(String result) {
        mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).child("avatar").setValue(result);
        updateUI(result);
    }

    private void updateUI(String result) {
        switch(result) {
            case "profile_beard_guy":
                avatarChoiceBtn.setImageResource(R.drawable.beard_guy);
                break;
            case "profile_afro_girl":
                avatarChoiceBtn.setImageResource(R.drawable.afro_girl);
                break;
            case "profile_business_guy":
                avatarChoiceBtn.setImageResource(R.drawable.business_guy);
                break;
            case "profile_brunette_girl":
                avatarChoiceBtn.setImageResource(R.drawable.brunette_girl);
                break;
            case "profile_glasses_guy":
                avatarChoiceBtn.setImageResource(R.drawable.glasses_guy);
                break;
            case "profile_headphones_girl":
                avatarChoiceBtn.setImageResource(R.drawable.headphones_girl);
                break;
            case "profile_moustache_guy":
                avatarChoiceBtn.setImageResource(R.drawable.moustache_guy);
                break;
            case "profile_ponytail_girl":
                avatarChoiceBtn.setImageResource(R.drawable.ponytail_girl);
                break;
        }

    }

    private void setAvatarInUI() {
        mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).child("avatar").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String s = dataSnapshot.getValue(String.class);
                        updateUI(s);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }

    private void setUserData() {
        mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Integer i = dataSnapshot.child("points").getValue(Integer.class);
                        String fName = dataSnapshot.child("firstName").getValue(String.class);
                        //String lName = dataSnapshot.child("lastName").getValue(String.class);
                        setUserData(i, fName);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }

    private void setUserData(Integer i, String fName) {
        points.setText("You've earned " + i.toString() + " points!");
        userName.setText("Hello, " + fName);
    }
}
