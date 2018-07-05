package com.example.sara.marketer.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Raad on 8/28/17.
 */

public class ChangeFragment {


    public static void changeFragment(FragmentActivity mContext, Fragment fragment, int viewId, boolean addToBackStack) {
        FragmentManager manager = mContext.getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        if (addToBackStack) {
            trans.addToBackStack(fragment.getClass().getSimpleName());
        }
        trans.replace(viewId, fragment, fragment.getClass().getSimpleName());
        trans.commit();
    }

    public static void removeFragment(FragmentActivity mContext, Fragment fragment, View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        FragmentManager manager = mContext.getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.remove(fragment);
        trans.commit();
        manager.popBackStack();
    }


}
