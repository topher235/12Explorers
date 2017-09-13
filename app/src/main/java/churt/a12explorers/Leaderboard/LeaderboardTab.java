package churt.a12explorers.Leaderboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import churt.a12explorers.R;

/**
 * Created by Christopher Hurt on 9/12/2017.
 */

public class LeaderboardTab extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        return rootView;
    }
}
