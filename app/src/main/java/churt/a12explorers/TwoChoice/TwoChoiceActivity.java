package churt.a12explorers.TwoChoice;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import churt.a12explorers.FourChoice.FourChoiceActivity;
import churt.a12explorers.R;

/**
 * Created by Christopher Hurt on 9/12/2017.
 */

public class TwoChoiceActivity extends Activity implements View.OnClickListener {

    private final static String TAG = TwoChoiceActivity.class.getSimpleName();
    private TextView question;
    private Button choice1, choice2;
    private TextView isCorrect;
    private String answer;
    private String[][] questionList;
    private int points;
    private int numQuestionsAnsweredToday = 0;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_choice);

        //Grab user info from DB
        mAuth = FirebaseAuth.getInstance();
        getUserDataFromDB();

        //Grab items from xml
        question = (TextView) findViewById(R.id.two_choice_question);
        choice1 = (Button) findViewById(R.id.two_choice_btn1);
        choice2 = (Button) findViewById(R.id.two_choice_btn2);
        isCorrect = (TextView) findViewById(R.id.two_choice_is_correct);

        //Set onClick listeners
        choice1.setOnClickListener(this);
        choice2.setOnClickListener(this);

        AssetManager am = getBaseContext().getAssets();
        setUpArray(am);//read in questions/answers from file

        //Set first question and choices
        question.setText(questionList[0][0]);
        choice1.setText(questionList[0][1]);
        choice2.setText(questionList[0][2]);
        answer = questionList[0][3];
        isCorrect.setText("");

    }

    private void getUserDataFromDB() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get points and use the values to update the user
                        Integer i = dataSnapshot.child("points").getValue(Integer.class);
                        Integer j = dataSnapshot.child("numFourChoiceAnswered").getValue(Integer.class);
                        Log.d(TAG, "Value is: " + i);
                        setUserData(i, j);
                        // ...
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting points failed, log a message
                        Log.w(TAG, "updateUserPoints:onCancelled", databaseError.toException());
                        // ...
                    }
                });
    }

    /**
     * Sets field variables
     * @param points
     * @param numQuestionsAnsweredToday
     */
    private void setUserData(int points, int numQuestionsAnsweredToday) {
        this.points = points;
        this.numQuestionsAnsweredToday = numQuestionsAnsweredToday;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.two_choice_btn1:
                if(choice1.getText().equals(answer)) {
                    userIsCorrect(choice1);
                } else {
                    userIsWrong(choice1);
                }
                break;
            case R.id.two_choice_btn2:
                if(choice2.getText().equals(answer)) {
                    userIsCorrect(choice2);
                } else {
                    userIsWrong(choice2);
                }
                break;
        }
    }

    /**
     * Reads in questions from a text file.
     * Stores them in a 2d array as question, choice,
     * choice, choice, choice, answer.
     * @param am
     */
    private void setUpArray(AssetManager am) {
        try {
            InputStreamReader is = new InputStreamReader(am.open("twochoicequestions.txt"));
            BufferedReader bufferedReader = new BufferedReader(is);
            String numQuestions = bufferedReader.readLine();

            questionList = new String[Integer.parseInt(numQuestions)][6];

            for(int i = 0; i < Integer.parseInt(numQuestions); i++) {
                String line = bufferedReader.readLine();
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(" = ");

                for(int j = 0; j < 6; j++) {
                    questionList[i][j] = lineScanner.next();
                }
            }
        } catch(FileNotFoundException ferr) {
            Log.d("FourChoiceActivity", "FILE NOT FOUND EXCEPTION");
        } catch(IOException ioerr) {
            Log.d(TAG, "IOEXCEPTION");
        }
    }

    /**
     * Called when the user has chosen the correct answer.
     * Update UI for ~five seconds.
     * Add points to their db entry.
     * Display next question.
     */
    private void userIsCorrect(final Button btn) {
        //Update user data locally
        points += 10;
        numQuestionsAnsweredToday++;
        updateUserInfoInDB();

        //Update UI for ~five seconds
        isCorrect.setText("Correct!");
        final Handler handler = new Handler();
        btn.setTextColor(Color.WHITE);
        btn.setBackgroundColor(Color.rgb(6, 155, 0));

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                btn.setTextColor(Color.BLACK);
                btn.setBackgroundColor(Color.LTGRAY);
                displayNextQuestion();
            }
        }, 3000);

    }

    /**
     * Called when the user has chosen the incorrect answer.
     * Update UI for ~five seconds.
     * Display next question.
     */
    private void userIsWrong(final Button btn) {
        //Update user data locally
        numQuestionsAnsweredToday++;
        updateUserInfoInDB();

        //Update UI for ~five seconds
        isCorrect.setText("Incorrect");
        final Handler handler = new Handler();
        btn.setTextColor(Color.WHITE);
        btn.setBackgroundColor(Color.RED);

        final Button actual = actualAnswer();
        actual.setTextColor(Color.WHITE);
        actual.setBackgroundColor(Color.rgb(6, 155, 0));

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                btn.setTextColor(Color.BLACK);
                btn.setBackgroundColor(Color.LTGRAY);
                actual.setTextColor(Color.BLACK);
                actual.setBackgroundColor(Color.LTGRAY);
                displayNextQuestion();
            }
        }, 3000);
    }

    /**
     * Gets the button that contains the correct answer
     * @return
     */
    private Button actualAnswer() {
        if(choice1.getText().equals(answer)) {
            return choice1;
        } else {
            return choice2;
        }
    }

    /**
     * Sets the new values in the DB
     */
    private void updateUserInfoInDB() {
        mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).child("points").setValue(points);
        mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).child("numFourChoiceAnswered").setValue(numQuestionsAnsweredToday);
    }

    /**
     * Updates the UI with the next available question.
     */
    private void displayNextQuestion() {
        try {
            question.setText(questionList[numQuestionsAnsweredToday][0]);
            choice1.setText(questionList[numQuestionsAnsweredToday][1]);
            choice2.setText(questionList[numQuestionsAnsweredToday][2]);
            answer = questionList[numQuestionsAnsweredToday][3];
            isCorrect.setText("");
        } catch(ArrayIndexOutOfBoundsException err) {
            updateUIOutOfQuestions();
        }
    }

    /**
     * Called when there are no more questions for the user.
     */
    private void updateUIOutOfQuestions() {
        question.setText("No more questions available today. \n Come back tomorrow for more questions.");
        choice1.setVisibility(View.INVISIBLE);
        choice2.setVisibility(View.INVISIBLE);
        isCorrect.setVisibility(View.INVISIBLE);
    }
}
