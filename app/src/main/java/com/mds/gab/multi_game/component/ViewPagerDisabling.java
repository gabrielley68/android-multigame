package com.mds.gab.multi_game.component;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ViewPagerDisabling extends ViewPager {
    private boolean pagingEnabled = true;


    public ViewPagerDisabling(@NonNull Context context) {
        super(context);
    }

    public ViewPagerDisabling(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public void setPagingEnabled(boolean pagingEnabled){
        this.pagingEnabled = pagingEnabled;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return pagingEnabled && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return pagingEnabled && super.onTouchEvent(ev);
    }
}
