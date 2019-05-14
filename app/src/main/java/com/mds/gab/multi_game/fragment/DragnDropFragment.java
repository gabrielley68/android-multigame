package com.mds.gab.multi_game.fragment;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mds.gab.multi_game.R;
import com.mds.gab.multi_game.ScoreActivity;
import com.mds.gab.multi_game.utils.ActivityUtils;
import com.mds.gab.multi_game.utils.TimerUtils;

public class DragnDropFragment extends Fragment {

    private static final int RED = 0;
    private static final int GREEN = 1;
    private static final int PURPLE = 2;
    private static final int BLACK = 3;
    private static final int BLUE = 4;
    private static final int GRAY = 5;

    private TimerUtils timerUtils;
    private int currentColor = (int)(Math.random() * 6);
    private int score = 0;

    private ImageView draggableIv;
    private TextView scoreTextView;
    private TextView timerTextView;

    private View redView;
    private View greenView;
    private View purpleView;
    private View blackView;
    private View blueView;
    private View grayView;
    private RelativeLayout blankView;

    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_drag_n_drop, container, false);

        scoreTextView = view.findViewById(R.id.score_dragndrop);
        timerTextView = view.findViewById(R.id.timer_dragndrop);


        redView = view.findViewById(R.id.red);
        redView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                if(event.getAction() == DragEvent.ACTION_DROP) {
                    droppedOnView(RED);
                }
                return true;
            }
        });

        greenView = view.findViewById(R.id.green);
        greenView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                if(event.getAction() == DragEvent.ACTION_DROP) {
                    droppedOnView(GREEN);
                }
                return true;
            }
        });

        purpleView = view.findViewById(R.id.purple);
        purpleView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                if(event.getAction() == DragEvent.ACTION_DROP){
                    droppedOnView(PURPLE);
                }
                return true;
            }
        });

        blackView = view.findViewById(R.id.black);
        blackView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                if(event.getAction() == DragEvent.ACTION_DROP){
                    droppedOnView(BLACK);
                }
                return true;
            }
        });

        blueView = view.findViewById(R.id.blue);
        blueView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                if(event.getAction() == DragEvent.ACTION_DROP){
                    droppedOnView(BLUE);
                }
                return true;
            }
        });

        grayView = view.findViewById(R.id.gray);
        grayView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                if(event.getAction() == DragEvent.ACTION_DROP){
                    droppedOnView(GRAY);
                }
                return true;
            }
        });

        blankView = view.findViewById(R.id.blank);
        blankView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                if(event.getAction() == DragEvent.ACTION_DROP){
                    draggableIv.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });


        draggableIv = view.findViewById(R.id.draggable);
        draggableIv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ClipData.Item item = new ClipData.Item((CharSequence)v.getTag());

                ClipData dragData = new ClipData(
                        (CharSequence)v.getTag(),
                        new String[] {ClipDescription.MIMETYPE_TEXT_PLAIN},
                        item
                );

                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(draggableIv);

                v.startDrag(dragData, shadowBuilder, null, 0);
                v.setVisibility(View.INVISIBLE);
                return true;
            }
        });

        timerUtils = new TimerUtils(timerTextView, 20) {
            @Override
            public void end() {
                Intent intent = new Intent(context, ScoreActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("score",score);
                bundle.putString("title", getString(R.string.dragndrop));
                intent.putExtras(bundle);
                ActivityUtils.launchActivity((AppCompatActivity)context, intent);
            }
        };

        timerUtils.beginTimer();

        colorizeDraggable();

        //Vue créée mais layout pas calculé
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                randomPosition();
            }
        });

        return view;
    }

    private void droppedOnView(int color){
        draggableIv.setVisibility(View.VISIBLE);
        if(color == currentColor){
            score++;
            scoreTextView.setText(getText(R.string.score)  + Integer.toString(score));
            nextColor();
        }
    }

    private void nextColor(){
        currentColor = (int)(Math.random() * 6);
        colorizeDraggable();
        randomPosition();
    }

    private void colorizeDraggable(){
        switch(currentColor){
            case RED:
                draggableIv.getBackground().clearColorFilter();
                draggableIv.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                break;
            case GREEN:
                draggableIv.getBackground().clearColorFilter();
                draggableIv.getBackground().setColorFilter(getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
                break;
            case PURPLE:
                draggableIv.getBackground().clearColorFilter();
                draggableIv.getBackground().setColorFilter(getResources().getColor(R.color.purple), PorterDuff.Mode.SRC_ATOP);
                break;
            case BLACK:
                draggableIv.getBackground().clearColorFilter();
                draggableIv.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
                break;
            case BLUE:
                draggableIv.getBackground().clearColorFilter();
                draggableIv.getBackground().setColorFilter(getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_ATOP);
                break;
            case GRAY:
                draggableIv.getBackground().clearColorFilter();
                draggableIv.getBackground().setColorFilter(getResources().getColor(R.color.gray), PorterDuff.Mode.SRC_ATOP);
                break;
        }

    }

    private void randomPosition(){
        double maxWidth = blankView.getWidth() - draggableIv.getWidth();
        double maxHeight = blankView.getHeight() - draggableIv.getHeight();

        double randWidth = Math.random() * (maxWidth + 1);
        double randHeight = Math.random() * (maxHeight + 1);

        draggableIv.setX((float)randWidth);
        draggableIv.setY((float)randHeight);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timerUtils.stopTimer();
    }
}
