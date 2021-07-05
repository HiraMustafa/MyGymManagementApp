package com.example.project.Utills;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.List;

public class PrefHelper {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
static PrefHelper _this;
    public PrefHelper(Context context) {
        sharedPreferences=context.getSharedPreferences("Pref",Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }
    public static PrefHelper getInstance(Context context){
        if(_this==null){
            _this=new PrefHelper(context);
        }
        return _this;
    }


    public void saveString(String key,String value){
        editor.putString(key, value).apply();
    }
    public <T>void saveObject(String key,T value){
        editor.putString(key, new Gson().toJson(value)).apply();
    }
    public String getString(String key){
        return sharedPreferences.getString(key,null);
    }
    public <T>T getObject(String key,Class<T> clazz){
        String string = sharedPreferences.getString(key, null);
        if(string!=null){
            return (T)new Gson().fromJson(string,clazz);
        }
        return null;
    }
//    public  List<productModel> getCartList(){
//        String cartItems = getString("cartItems");
//        if(cartItems!=null){
//            Utils.tl
//        }
//    }
}
