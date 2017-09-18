package churt.a12explorers.Leaderboard;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import churt.a12explorers.Helper.User;
import churt.a12explorers.R;

/**
 * Created by Christopher Hurt on 9/12/2017.
 *
 * TODO: Test with more accounts.
 */

public class LeaderboardTab extends Fragment {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ArrayList<User> listUser;
    private TableLayout tableLayout;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        //Initialize firebase components
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        if(mAuth.getCurrentUser() != null) {
            listUser = new ArrayList<>();
            tableLayout = (TableLayout) rootView.findViewById(R.id.table);
            populateTable();
        }

        return rootView;
    }

    /**
     * Populates the table with the top 100 users, ranked by points.
     */
    private void populateTable() {
        //START LOADING ICON
        mDatabase.child("Users").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            createUserObject(ds);
                        }
                        displayLeaderboard();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
        //END LOADING ICON
    }

    private void createUserObject(DataSnapshot ds) {
        String avatar = ds.child("avatar").getValue(String.class);
        String fName = ds.child("firstName").getValue(String.class);
        String lName = ds.child("lastName").getValue(String.class);
        Integer points = ds.child("points").getValue(Integer.class);
        Integer numFourChoice = ds.child("numFourChoiceAnswered").getValue(Integer.class);
        Integer numTwoChoice = ds.child("numTwoChoiceAnswered").getValue(Integer.class);
        insertionSort(new User(fName, lName, "", avatar, points, numFourChoice, numTwoChoice));
    }

    private void insertionSort(User user) {
        if(listUser.isEmpty()) {
            listUser.add(user);
        } else {
            User tmp;
            for(int i = 1; i < listUser.size(); i++) {
                for(int j = i; j > 0; j--) {
                    if(listUser.get(j).points < listUser.get(j-1).points) {
                        tmp = listUser.get(j);
                        listUser.set(j, listUser.get(j-1));
                        listUser.set(j-1, tmp);
                    }
                }
            }
        }
    }

    private void displayLeaderboard() {
        for(User u : listUser) {
            Integer pos = 1;
            int j = 0;
            //Table row
            TableRow tr = new TableRow(getActivity());

            //Left TextView
            TextView tvLeft = new TextView(getActivity());
            tvLeft.setId(j++);
            tvLeft.setPadding(3, 3, 3, 3);
            tvLeft.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.shape, null));
            tvLeft.setText(pos.toString());
            tvLeft.setTextColor(Color.WHITE);
            tvLeft.setTextSize(20);
            tvLeft.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


            //Left TextView
            TextView tvCenter = new TextView(getActivity());
            tvCenter.setId(j++);
            tvCenter.setPadding(3,3,3,3);
            tvCenter.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.shape, null));
            tvCenter.setText(u.firstName + " " + u.lastName);
            tvCenter.setTextColor(Color.WHITE);
            tvCenter.setTextSize(20);
            tvCenter.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            //Right TextView
            TextView tvRight = new TextView(getActivity());
            tvRight.setId(j++);
            tvRight.setPadding(3,3,3,3);
            tvRight.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.shape, null));
            tvRight.setText(String.valueOf(u.points));
            tvRight.setTextColor(Color.WHITE);
            tvRight.setTextSize(20);
            tvRight.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            //Add views to row
            tr.addView(tvLeft);
            tr.addView(tvCenter);
            tr.addView(tvRight);

            //Add row to table
            tableLayout.addView(tr);
        }
    }
}
