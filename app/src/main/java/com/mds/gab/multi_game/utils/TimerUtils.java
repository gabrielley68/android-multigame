package com.mds.gab.multi_game.utils;

import android.os.Handler;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public abstract class TimerUtils {

    int TIME_BEFORE_FIRST_EXECUTION = 1000;
    int DELAY_BETWEEN_TRIGGER = 1000;
    int timeLeft;
    TextView timerTv;
    TimerTask timerTask;

    public TimerUtils(TextView timerTv, int duration){
        this.timerTv = timerTv;
        this.timeLeft = duration;
    }

    public void beginTimer(){
        final Handler handler = new Handler();
        Timer timer = new Timer();

        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(timeLeft == 0){
                            end();
                        } else {
                            timerTv.setText("Temps restant : " + Integer.toString(timeLeft) + "s");
                            timeLeft--;
                        }
                    }
                });
            }
        };

        timer.schedule(timerTask, TIME_BEFORE_FIRST_EXECUTION, DELAY_BETWEEN_TRIGGER);

    }

    public void stopTimer(){
        timerTask.cancel();
    }

    public abstract void end();
}
