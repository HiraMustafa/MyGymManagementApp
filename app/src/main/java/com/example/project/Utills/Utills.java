package com.example.project.Utills;

import android.app.ProgressDialog;
import android.content.Context;

public class Utills {
    static ProgressDialog progressDialog;
    public static void showDialog(Context context){
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
    }
    public static void dissmiss(){
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }
}
