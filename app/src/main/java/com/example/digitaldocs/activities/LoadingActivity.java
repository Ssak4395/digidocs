package com.example.digitaldocs.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.digitaldocs.R;

public class LoadingActivity {
    private Activity activity;
    private AlertDialog alertDialog;

    LoadingActivity(Activity activity) {
        this.activity = activity;
    }

    void startLoadingAnimation() {
        //Declare a builder to start the dialog activity creation
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        //Declare inflater object, to explode layout on screen
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.custom_dialog,null));
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();
    }

    void dismissDialog() {
        alertDialog.dismiss();
    }
}
