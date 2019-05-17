package com.mds.gab.multi_game.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mds.gab.multi_game.R;
import com.mds.gab.multi_game.utils.FragmentUtils;

public class BeginGameFragment extends Fragment {

    private String gameName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_begin_game, container, false);

        gameName = getArguments() != null ? getArguments().getString("name") : null;

        TextView title = view.findViewById(R.id.begin_title);
        title.setText(gameName);

        Button button = view.findViewById(R.id.begin_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameName.equals(getString(R.string.fast_tap)) || gameName.equals(getString(R.string.swipe))) {
                    TapAndSwipeGameFragment newFragment = new TapAndSwipeGameFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("name", gameName);
                    newFragment.setArguments(bundle);
                    FragmentUtils.addFragmentToFragment(BeginGameFragment.this, newFragment, R.id.fast_tap_container, gameName);
                }
                else if(gameName.equals(getString(R.string.dragndrop))){
                    DragnDropFragment newFragment = new DragnDropFragment();
                    FragmentUtils.addFragmentToFragment(BeginGameFragment.this, newFragment, R.id.fast_tap_container, gameName);
                }
                else if(gameName.equals(getString(R.string.ipac_game))){
                    IpacGameFragment newFragment = new IpacGameFragment();
                    FragmentUtils.addFragmentToFragment(BeginGameFragment.this, newFragment, R.id.fast_tap_container, gameName);
                }
            }
        });

        return view;
    }
}
