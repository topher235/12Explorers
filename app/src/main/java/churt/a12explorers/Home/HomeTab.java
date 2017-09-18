package churt.a12explorers.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import churt.a12explorers.FourChoice.FourChoiceActivity;
import churt.a12explorers.R;
import churt.a12explorers.TwoChoice.TwoChoiceActivity;

/**
 * Created by Christopher Hurt on 9/12/2017.
 */

public class HomeTab extends Fragment {
    private Button twoChoiceBtn;
    private Button fourChoiceBtn;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        twoChoiceBtn = (Button) rootView.findViewById(R.id.home_two_choice_btn);
        twoChoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TwoChoiceActivity.class));
            }
        });

        fourChoiceBtn = (Button) rootView.findViewById(R.id.home_four_choice_btn);
        fourChoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), FourChoiceActivity.class));
            }
        });

        return rootView;
    }
}
