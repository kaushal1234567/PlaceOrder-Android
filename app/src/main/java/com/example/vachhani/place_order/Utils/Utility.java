package com.example.vachhani.place_order.Utils;

import android.app.Activity;
import android.app.ProgressDialog;



public class Utility {
    private static ProgressDialog pd;
    public static ProgressDialog getDialog(Activity activity) {
        pd = new ProgressDialog(activity);
        pd.setMessage("Please Wait...");
        pd.setIndeterminate(true);
        pd.setCancelable(false);
        return pd;
    }

    public static String api="http://192.168.43.229/API/";
}
