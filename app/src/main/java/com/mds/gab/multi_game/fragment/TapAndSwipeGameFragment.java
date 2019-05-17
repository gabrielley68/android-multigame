package com.mds.gab.multi_game.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mds.gab.multi_game.MainActivity;
import com.mds.gab.multi_game.R;
import com.mds.gab.multi_game.ScoreActivity;
import com.mds.gab.multi_game.utils.ActivityUtils;
import com.mds.gab.multi_game.utils.TimerUtils;

public class TapAndSwipeGameFragment extends Fragment {


    private int score = 0;
    private double random = Math.random();
    private float x1,x2,y1,y2;
    private static final int MIN_DISTANCE = 100;
    private static final int MAX_DISTANCE = 1000;
    private TextView textTv;
    private TextView scoreTv;
    private TimerUtils timerUtils;

    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_fast_tap_game, container, false);

        final String name = getArguments() != null ? getArguments().getString("name") : null;

        TextView timerTv = view.findViewById(R.id.fast_tap_game_timer);
        textTv = view.findViewById(R.id.fast_tap_game_text);
        scoreTv = view.findViewById(R.id.fast_tap_game_score);
        LinearLayout containerLl = view.findViewById(R.id.fast_tap_game_container);

        if(name != null && name.equals(getString(R.string.fast_tap))) {
            nextTap();

            containerLl.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (random < 0.25) {
                        addScore();
                        nextTap();
                    }
                    return true;
                }
            });

            containerLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (random >= 0.25) {
                        addScore();
                        nextTap();
                    }
                }
            });
        } else if(name.equals(getString(R.string.swipe))) {
            ((MainActivity)getActivity()).viewPager.setPagingEnabled(false);
            nextSwipe();
            containerLl.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch(event.getAction())
                    {
                        case MotionEvent.ACTION_DOWN:
                            x1 = event.getX();
                            y1 = event.getY();
                        break;
                        case MotionEvent.ACTION_UP:
                            x2 = event.getX();
                            y2 = event.getY();
                            float deltaX = x2-x1;
                            float deltaY = y2-y1;
                            if(Math.abs(deltaX) >= MIN_DISTANCE && Math.abs(deltaX) <= MAX_DISTANCE){
                                if(deltaX < 0 && random < 0.25){
                                    addScore();
                                    nextSwipe();
                                } else if(deltaX > 0 && random >= 0.5 && random < 0.75) {
                                    addScore();
                                    nextSwipe();
                                }
                            }
                            if(Math.abs(deltaY) >= MIN_DISTANCE && Math.abs(deltaY) <= MAX_DISTANCE){
                                if(deltaY < 0 && random >= 0.25 && random < 0.5){
                                    addScore();
                                    nextSwipe();
                                }
                                else if(deltaY > 0 && random >= 0.75){
                                    addScore();
                                    nextSwipe();
                                }
                            }
                        break;
                    }
                    return true;
                }
            });
        }

        timerUtils = new TimerUtils(timerTv, 20) {
            @Override
            public void end() {
                Intent intent = new Intent(context, ScoreActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("score", score);
                bundle.putString("title", name);
                intent.putExtras(bundle);
                ActivityUtils.launchActivity((AppCompatActivity)context, intent);
            }
        };

        timerUtils.beginTimer();

        return view;
    }

    private void addScore(){
        score++;
        scoreTv.setText(getText(R.string.score) + Integer.toString(score));
    }

    private void nextTap(){
        random = Math.random();
        if(random < 0.25){
            textTv.setText("LONG TAP");
        } else {
            textTv.setText("TAP");
        }
    }

    private void nextSwipe(){
        random = Math.random();
        if(random < 0.25){
            textTv.setText("Left");
        }
        else if(random >= 0.25 && random < 0.5){
            textTv.setText("Up");
        }
        else if(random >= 0.5 && random < 0.75){
            textTv.setText("Right");
        }
        else {
            textTv.setText("Down");
        }
    }

    @Override
    public void onDestroy() {
        ((MainActivity)getActivity()).viewPager.setPagingEnabled(true);
        timerUtils.stopTimer();
        super.onDestroy();
    }
}
