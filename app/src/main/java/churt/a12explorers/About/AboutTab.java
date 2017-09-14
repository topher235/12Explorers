package churt.a12explorers.About;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import churt.a12explorers.R;

/**
 * Created by Christopher Hurt on 9/12/2017.
 */

public class AboutTab extends Fragment {
    private TextView description;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        description = (TextView) rootView.findViewById(R.id.about_textview);
        setDescriptionText();

        return rootView;
    }

    private void setDescriptionText() {
        description.setText("Journey, Explore, Discover\n" +
                "\n" +
                "Welcome to the 12 Explorers Project presented by Christopher Newport University’s" +
                " Public History Center. The human connection to the sea has been recorded since the " +
                " earliest of times. Throughout history, the ocean has been a critical resource," +
                "leading many to navigate and travel on it, and explore its depths. Our purpose is " +
                "to showcase 12 explorers who represent a part of maritime history, culture, and " +
                "innovation around the world. Journey with us as we travel back in time and learn " +
                "how the water brings us together in this global age.\n" +
                "\n" +
                "Learn more by visiting https://12explorers.org");
        description.setTextSize(16);
    }
}
