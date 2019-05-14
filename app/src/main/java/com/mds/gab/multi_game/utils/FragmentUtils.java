package com.mds.gab.multi_game.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.mds.gab.multi_game.R;

public class FragmentUtils {

    public static void addFragmentToActivity(FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId, String tag)
    {
        FragmentTransaction transaction;
        transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.add(frameId, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void addFragmentToFragment(Fragment parentFragment, @NonNull Fragment fragment, int frameId, String tag)
    {
        FragmentTransaction transaction = parentFragment.getChildFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.add(frameId, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
