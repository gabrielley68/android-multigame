package com.mds.gab.multi_game.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mds.gab.multi_game.R;
import com.mds.gab.multi_game.ScoreActivity;
import com.mds.gab.multi_game.utils.ActivityUtils;

public class IpacGameFragment extends Fragment {

    private static final int NUMBER_OF_TRY = 10;

    private EditText numberEd;
    private TextView numberOfTryTv;
    private TextView moreOrLessTv;
    private Button validateButton;
    private int numberToFind;
    private int numberOfTry = NUMBER_OF_TRY;
    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ipac_game, container, false);

        numberOfTryTv = view.findViewById(R.id.main_number_try);
        numberEd = view.findViewById(R.id.main_input_number);
        moreOrLessTv = view.findViewById(R.id.main_more_or_less);
        validateButton = view.findViewById(R.id.main_validate);

        numberOfTryTv.setText(getResources().getString(R.string.number_try, numberOfTry));
        numberToFind = (int)(Math.random() * 100);

        numberEd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    validate();
                    closeKeyboard();
                }
                return false;
            }
        });

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
        return view;
    }

    private void validate(){
        if(!numberEd.getText().toString().isEmpty()) {
            int number = Integer.valueOf(numberEd.getText().toString());
            if (number == numberToFind) {
                launchFinish(numberOfTry);

            } else {
                numberOfTry--;

                if (numberOfTry == 0) {
                    launchFinish(numberOfTry);
                }

                numberOfTryTv.setText(getResources().getString(R.string.number_try, numberOfTry));
                numberEd.setText("");

                moreOrLessTv.setText(getResources().getString(number < numberToFind ? R.string.more : R.string.less));
                displayTextWithFade(moreOrLessTv);
            }
        } else {
            moreOrLessTv.setText(getResources().getString(R.string.error_empty_input));
        }
    }

    private void closeKeyboard(){
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    private void launchFinish(int score){
        Intent intent = new Intent(context, ScoreActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("score", score);
        bundle.putString("title", "Ipac Game");
        intent.putExtras(bundle);
        ActivityUtils.launchActivity((AppCompatActivity)context, intent);
    }

    private void displayTextWithFade(final View view){
        view.setVisibility(View.VISIBLE);
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideTextWithFade(view);
            }
        }, 1500);
    }

    private void hideTextWithFade(View view){
        view.setVisibility(View.INVISIBLE);
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out));
    }
}
