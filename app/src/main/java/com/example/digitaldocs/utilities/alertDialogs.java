package com.example.digitaldocs.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.digitaldocs.R;

public class alertDialogs {

    private Activity activity;
    private AlertDialog alertDialog;


    public alertDialogs(Activity activity){
        this.activity = activity;
    }

    public void explodeDialog()
    {


        //Declare a builder to start the dialog activity creation
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        //Declare inflator object, to explode layout on screen
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.custom_alert_dialog,null));
        builder.setCancelable(true);
        alertDialog = builder.create();
        alertDialog.show();
    }

   public void dismissDialog()
    {
        alertDialog.dismiss();
    }

}
