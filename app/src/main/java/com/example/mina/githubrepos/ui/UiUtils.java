package com.example.mina.githubrepos.ui;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.example.mina.githubrepos.R;

/**
 * Created by Mina on 9/17/2017.
 */

public class UiUtils {

    public static void loadSnackBar(String message, Activity activity) {

        Snackbar snackbar = Snackbar.make(
                activity.findViewById(android.R.id.content), message,
                Snackbar.LENGTH_LONG).setAction(
                activity.getString(R.string.done),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        snackbar.setActionTextColor(ContextCompat.getColor(activity, R.color.accent));
        /*View snackBarView = snackbar.getView();
        TextView tv = (TextView) snackBarView.findViewById(R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackBarView.setBackgroundColor(Color.parseColor("#323232"));*/
        snackbar.show();
    }
}
