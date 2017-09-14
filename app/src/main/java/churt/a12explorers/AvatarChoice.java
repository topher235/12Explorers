package churt.a12explorers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Christopher Hurt on 9/13/2017.
 */

public class AvatarChoice extends Activity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String userChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_choice);

        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Called when an image is clicked. Sets
     * the file name to userChoice.
     * @param view
     */
    public void getImage(View view) {
        switch(view.getId()) {
            case R.id.profile_beard_guy:
                userChoice = "profile_beard_guy";
                break;
            case R.id.profile_afro_girl:
                userChoice = "profile_afro_girl";
                break;
            case R.id.profile_business_guy:
                userChoice = "profile_business_guy";
                break;
            case R.id.profile_brunette_girl:
                userChoice = "profile_brunette_girl";
                break;
            case R.id.profile_glasses_guy:
                userChoice = "profile_glasses_guy";
                break;
            case R.id.profile_headphones_girl:
                userChoice = "profile_headphones_girl";
                break;
            case R.id.profile_moustache_guy:
                userChoice = "profile_moustache_guy";
                break;
            case R.id.profile_ponytail_girl:
                userChoice = "profile_ponytail_girl";
                break;
            default:
                userChoice = "profile_beard_guy";
        }
        setImage();
    }

    /**
     * Puts the image string choice into the database.
     */
    private void setImage() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).child("avatar").setValue(userChoice);
        navigateBackToAccountTab();
    }

    private void navigateBackToAccountTab() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", userChoice);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
