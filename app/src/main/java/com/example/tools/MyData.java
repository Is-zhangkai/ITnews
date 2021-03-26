package com.example.tools;

import android.content.Context;
import android.content.SharedPreferences;

public class MyData {
    private Context context;
    private static SharedPreferences mPreferences;
    private static SharedPreferences.Editor mEditor;
    private static MyData mSharedPreferencesUtil;


    public MyData(Context context) {
        this.context = context;
        mPreferences = this.context.getSharedPreferences("MY_DATA", Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public static MyData getInstance(Context context) {
        if (mSharedPreferencesUtil == null) {
            mSharedPreferencesUtil = new MyData(context);
        }
        return mSharedPreferencesUtil;
    }
    public void save_net(Boolean net){
        mEditor.putBoolean("NET",net);
        mEditor.commit();
    }

    public void save_check(Boolean check) {
        mEditor.putBoolean("CHECK", check);
        mEditor.commit();
    }
    public void save_sex(int sex) {
        mEditor.putInt("SEX",sex);
        mEditor.commit();
    }

    public void save_token(String token) {
        mEditor.putString("TOKEN", token);
        mEditor.commit();
    }

    public void save_name(String name){
        mEditor.putString("NAME",name);
        mEditor.commit();
    }
    public void save_info(String info){
        mEditor.putString("INFO",info);
        mEditor.commit();
    }
    public void save_fans(int num){
        mEditor.putInt("FANS",num);
        mEditor.commit();
    }
    public void save_attentions(int num){
        mEditor.putInt("ATTENTIONS",num);
        mEditor.commit();
    }
    public void save_id(int my){
        mEditor.putInt("MY",my);
        mEditor.commit();
    }

    public String load_token() {
        return mPreferences.getString("TOKEN", "NO");
    }
    public Boolean load_check() {
        return mPreferences.getBoolean("CHECK", false);
    }
    public int load_sex() {
        return mPreferences.getInt("SEX", 0);
    }
    public Boolean load_net() {
        return mPreferences.getBoolean("NET", true);
    }
    public String load_name(){
        return mPreferences.getString("NAME","暂无昵称");
    }
    public String load_info(){
        return mPreferences.getString("INFO","写点什么吧");
    }
    public int load_fans(){return mPreferences.getInt("FANS",0);}
    public int load_attentions(){return mPreferences.getInt("ATTENTIONS",0);}
    public int load_id(){return mPreferences.getInt("MY",1);}

}